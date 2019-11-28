package com.doublez.ontologyreasoning.Service;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @program: OntologyReasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/11/13
 **/
public class OntologyReasoner_for_LabDataService {
    public static Hashtable<String, String> prefix_map = new Hashtable<String, String>();
    public static Vector<Vector<String>> stmt_container = new Vector<Vector<String>>();
    public static Vector<String> buf_container = new Vector<String>();

    public static void init_map(){
        /**
         * @description: 定义 @prefix，通过Hashtable key-value对实现
         *
         * @return : void
         **/
        prefix_map.put("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "type");
        prefix_map.put("http://www.w3.org/2000/01/rdf-schema#domain", "domain");
        prefix_map.put("http://www.w3.org/2000/01/rdf-schema#range", "range");
        prefix_map.put("http://www.w3.org/2002/07/owl#Class", "Class");
        prefix_map.put("http://www.w3.org/2002/07/owl#ObjectProperty", "ObjectProperty");

        prefix_map.put("http://namespace/10.60.38.181/supervises", "rel:supervises");
        prefix_map.put("http://pods/10.60.38.181/contains", "rel:contains");
        prefix_map.put("http://pods/10.60.38.181/deployed_in", "rel:deployed_in");
        prefix_map.put("http://pods/10.60.38.181/provides", "rel:provides");
        prefix_map.put("http://containers/10.60.38.181/cc_profile", "rel:cc_profile");
        prefix_map.put("http://services/10.60.38.181/ss_profile", "rel:ss_profile");
        prefix_map.put("http://servers/10.60.38.181/manage", "rel:manage");
        prefix_map.put("http://environment/10.60.38.181/has", "rel:has");
//        prefix_map.put("http://localhost/KGns/relationship/supervises", "rel:supervises");
//        prefix_map.put("http://localhost/KGns/relationship/contains", "rel:contains");
//        prefix_map.put("http://localhost/KGns/relationship/deployed_in", "rel:deployed_in");
//        prefix_map.put("http://localhost/KGns/relationship/provides", "rel:provides");
//        prefix_map.put("http://localhost/KGns/relationship/cc_profile", "rel:cc_profile");
//        prefix_map.put("http://localhost/KGns/relationship/ss_profile", "rel:ss_profile");
//        prefix_map.put("http://localhost/KGns/relationship/manage", "rel:manage");
//        prefix_map.put("http://localhost/KGns/relationship/has", "rel:has");

        prefix_map.put("http://localhost/KGns/Pod", "Pod");
        prefix_map.put("http://localhost/KGns/Container", "Container");
        prefix_map.put("http://localhost/KGns/Service", "Service");
        prefix_map.put("http://localhost/KGns/Namespace", "Namespace");
        prefix_map.put("http://localhost/KGns/Server", "Server");
        prefix_map.put("http://localhost/KGns/Environment", "Environment");

//        prefix_map.put("http://localhost/KGns/Pod/1", "pod1");
//        prefix_map.put("http://localhost/KGns/Pod/2", "pod2");
//        prefix_map.put("http://localhost/KGns/Container/1", "container1");
//        prefix_map.put("http://localhost/KGns/Container/2", "container2");
//        prefix_map.put("http://localhost/KGns/Container/3", "container3");
//        prefix_map.put("http://localhost/KGns/Container/4", "container4");
//        prefix_map.put("http://localhost/KGns/Service/1", "service1");
    }

    public static void init_container(){
        /**
         * @description: 初始化结果容器，将输出数据分为四类（Class ObjectProperty object relation）
         *
         * @return : void
         **/
        for (int i = 0; i < 4; i++) {
            Vector<String> v = new Vector<String>();
            stmt_container.add(v);
        }
        stmt_container.get(0).add("# Class");
        stmt_container.get(1).add("# ObjectProperty");
        stmt_container.get(2).add("# object");
        stmt_container.get(3).add("# relation");
    }

    public static void add_into_container(Statement stmt){
        /**
         * @description: 将三元组进行过滤，加入到结果容器中
         *
         * @param stmt : 目标三元组
         *
         * @return : void
         **/
        String  subject   = stmt.getSubject().toString();
        String  predicate = stmt.getPredicate().toString();
        String  object    = stmt.getObject().toString();

        subject = prefix_map.containsKey(subject)?prefix_map.get(subject):subject;
        predicate = prefix_map.containsKey(predicate)?prefix_map.get(predicate):predicate;
        object = prefix_map.containsKey(object)?prefix_map.get(object):object;

        if(object.equals("Class") &&                            //类信息
                (subject.equals("Container") || subject.equals("Pod") || subject.equals("Service") ||
                        subject.equals("Server") || subject.equals("Environment") || subject.equals("Namespace"))){
            stmt_container.get(0).add(subject + " " + predicate + " " + object);
        }else if((subject.substring(0,3).equals("rel")) &&      //属性信息
                (predicate.equals("type") || predicate.equals("domain") || predicate.equals("range")) &&
                (object.equals("ObjectProperty") ||
                        object.equals("Container") || object.equals("Pod") || object.equals("Service") ||
                        object.equals("Server") || object.equals("Namespace") || object.equals("Environment"))){
            stmt_container.get(1).add(subject + " " + predicate + " " + object);
        }else if(predicate.equals("type") &&                     //对象
                (object.equals("Container") || object.equals("Pod") || object.equals("Service"))){
            stmt_container.get(2).add(subject + " " + predicate + " " + object);
        }else if(predicate.substring(0,3).equals("rel")) {      //对象之间的关系
            stmt_container.get(3).add(subject + " " + predicate + " " + object);
        }else{
            System.out.println(stmt);
        }
    }

    public static void initAllTriples(Model model, boolean with_filter) {
        /**
         * @description: 逐个去除model中到三元组，按照要求加入容器中
         *
         * @param model : 本体model
         * @param with_filter : 是否启用过滤器
         *
         * @return : void
         **/
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement stmt = itr.nextStatement();

            if(with_filter){
                add_into_container(stmt);
            }else{
                buf_container.add(stmt.toString());
            }
        }
    }

    public static void outputAllTriples(Model model, String filename, boolean with_filter){
        /**
         * @description: 将三元组中到内容写入到指定文件中
         *
         * @param model : 本体model
         * @param filename : 用户指定到文件名
         * @param with_filter : 是否启用过滤器
         *
         * @return : void
         **/

        initAllTriples(model, with_filter);

        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if(with_filter){
                for(Vector<String> category : stmt_container){
                    Collections.sort(category);
                    for(String item : category){
                        bufferedWriter.write(item + "\n");
                    }
                    bufferedWriter.write("\n");
                }
            }else{
                for(String item : buf_container){
                    bufferedWriter.write(item + "\n");
                }
                bufferedWriter.write("\n");
            }


            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void OntologyReasoning(){
        /**
         * @description: 本体推理
         *
         * @return : void
         **/
        // 为本体创建Model
        Model ontologyModel = ModelFactory.createDefaultModel();
        ontologyModel.read("data/data_only.ttl");

        // 创建一个新Model将本体与实例数据进行合并
        Model fusionModel = ModelFactory.createDefaultModel();
        fusionModel.add(ontologyModel);

        // 输出推理前的数据
        System.out.println("Triples Before Reasoning:");
        outputAllTriples(fusionModel, "data/before_without_filter.txt", false);
        outputAllTriples(fusionModel, "data/before_with_filter.txt", true);

        buf_container.clear();
        stmt_container.clear();
        init_container();

        // 在合并后的数据模型上进行OWL推理
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel inf = ModelFactory.createInfModel(reasoner, fusionModel);

        // 输出推理后的数据
        System.out.println("Triples After Reasoning:");
        outputAllTriples(inf, "data/after_without_filter.txt", false);
        outputAllTriples(inf, "data/after_with_filter.txt", true);

        ontologyModel.close();
        fusionModel.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //添加prefix
        init_map();

        //初始化结果结构
        init_container();

        //进行本体推理
        OntologyReasoning();
    }
}