package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.NeoDao;
import com.tongji.knowledgereasoning.util.Operations;
import org.apache.commons.io.FileUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @program: knowledgereasoning
 * @description: Ontology Reasoning Service.
 * @author: Zhe Zhang
 * @create: 2019/12/04
 **/

@Service("OntologyReasonerService")
    public class OntologyReasonerServiceImp implements OntologyReasonerService {
    private Hashtable<String, String> prefix_map = new Hashtable<String, String>();
    private Vector<Vector<String>> stmt_container = new Vector<Vector<String>>();
    private Vector<String> buf_container = new Vector<String>();

    private Model ontologyModel;         // 为本体创建Model
    private Model fusionModel;           // 创建一个新Model将本体与实例数据进行合并
    private InfModel inf;                //在合并后的数据模型

    @Autowired
    private NeoDao neoDao;

    private void init_map() {
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

        prefix_map.put("http://localhost/KGns/Pod", "Pod");
        prefix_map.put("http://localhost/KGns/Container", "Container");
        prefix_map.put("http://localhost/KGns/Service", "Service");
        prefix_map.put("http://localhost/KGns/Namespace", "Namespace");
        prefix_map.put("http://localhost/KGns/Server", "Server");
        prefix_map.put("http://localhost/KGns/Environment", "Environment");

    }

    private void init_container() {
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

    private void add_into_container(Statement stmt) {
        /**
         * @description: 将三元组进行过滤，加入到结果容器中
         *
         * @param stmt : 目标三元组
         *
         * @return : void
         **/
        String subject = stmt.getSubject().toString();
        String predicate = stmt.getPredicate().toString();
        String object = stmt.getObject().toString();

        subject = prefix_map.containsKey(subject) ? prefix_map.get(subject) : subject;
        predicate = prefix_map.containsKey(predicate) ? prefix_map.get(predicate) : predicate;
        object = prefix_map.containsKey(object) ? prefix_map.get(object) : object;

        if (object.equals("Class") &&                            //类信息
                (subject.equals("Container") || subject.equals("Pod") || subject.equals("Service") ||
                        subject.equals("Server") || subject.equals("Environment") || subject.equals("Namespace"))) {
            stmt_container.get(0).add(subject + " " + predicate + " " + object);
        } else if ((subject.substring(0, 3).equals("rel")) &&      //属性信息
                (predicate.equals("type") || predicate.equals("domain") || predicate.equals("range")) &&
                (object.equals("ObjectProperty") ||
                        object.equals("Container") || object.equals("Pod") || object.equals("Service") ||
                        object.equals("Server") || object.equals("Namespace") || object.equals("Environment"))) {
            stmt_container.get(1).add(subject + " " + predicate + " " + object);
        } else if (predicate.equals("type") &&                     //对象
                (object.equals("Container") || object.equals("Pod") || object.equals("Service"))) {
            stmt_container.get(2).add(subject + " " + predicate + " " + object);
        } else if (predicate.substring(0, 3).equals("rel")) {      //对象之间的关系
            stmt_container.get(3).add(subject + " " + predicate + " " + object);
        } else {
            System.out.println(stmt);
        }
    }

    private void initAllTriples(Model model, boolean with_filter) {
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

            if (with_filter) {
                add_into_container(stmt);
            } else {
                buf_container.add(stmt.toString());
            }
        }
    }

    private void writeAllTriples(Model model, String filename, boolean with_filter) {
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

        try {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getPath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (with_filter) {
                for (Vector<String> category : stmt_container) {
                    Collections.sort(category);
                    for (String item : category) {
                        bufferedWriter.write(item + "\n");
                    }
                    bufferedWriter.write("\n");
                }
            } else {
                for (String item : buf_container) {
                    bufferedWriter.write(item + "\n");
                }
                bufferedWriter.write("\n");
            }


            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readOriginData() {
        //添加prefix
        init_map();

        //初始化结果结构
        init_container();

        ontologyModel = ModelFactory.createDefaultModel();
        ontologyModel.read("data/Ontology Reasoning/missing_data.ttl");

        fusionModel = ModelFactory.createDefaultModel();
        fusionModel.add(ontologyModel);
    }

    public void outputOriginTriples() throws IOException {
        // 输出推理前的数据
        System.out.println("Triples Before Reasoning:");
        writeAllTriples(fusionModel, "data/Ontology Reasoning/before_ontology_reasoning_without_filter.ttl", false);
        writeAllTriples(fusionModel, "data/Ontology Reasoning/before_ontology_reasoning_with_filter.ttl", true);

        //写入neo4j
        File source = new File("data/Ontology Reasoning/missing_data.ttl");
        File dest = new File("data/Ontology Reasoning/before_ontology_reasoning_for_neo4j.ttl");
        FileUtils.copyFile(source, dest);
    }

    public void outputOntologyTriples() {
        // 输出推理后的数据
        System.out.println("Triples After Reasoning:");
        writeAllTriples(inf, "data/Ontology Reasoning/after_ontology_reasoning_without_filter.ttl", false);
        writeAllTriples(inf, "data/Ontology Reasoning/after_ontology_reasoning_with_filter.ttl", true);

        //写入neo4j
        Vector<String> fresh_objects = new Vector<String>();

        File file = new File("data/Ontology Reasoning/after_ontology_reasoning_with_filter.ttl");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            boolean flag = false;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (tempString.equals("# object")) {
                    flag = true;
                }
                if (tempString.equals("# relation")) {
                    break;
                }
                if (flag) {
                    fresh_objects.add(tempString);
                }

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        Vector<String> refactor_objects = new Vector<String>();
        for (String line : fresh_objects) {
            String[] words = line.split(" ");
            if (words.length == 3) {
                String fresh_line = '<' + words[0] + "> rdf:type :" + words[2] + " .";
                refactor_objects.add(fresh_line);
            }
        }

        try {
            File out_file = new File("data/Ontology Reasoning/after_ontology_reasoning_for_neo4j.ttl");

            FileWriter fileWriter = new FileWriter(out_file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            File copy_file = new File("data/Ontology Reasoning/before_ontology_reasoning_for_neo4j.ttl");
            BufferedReader copy_reader = null;
            try {
                copy_reader = new BufferedReader(new FileReader(copy_file));
                String tempString = null;

                // 一次读入一行，直到读入null为文件结束
                while ((tempString = copy_reader.readLine()) != null) {
                    if (tempString.equals("## objects")) {
                        break;
                    } else {
                        bufferedWriter.write(tempString + '\n');
                    }
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }


            for (String line : refactor_objects) {
                bufferedWriter.write(line + '\n');
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OntologyReasoning_for_origindata() {
        /**
         * @description: 本体推理
         *
         * @return : void
         **/
        buf_container.clear();
        stmt_container.clear();
        init_container();

        // 在合并后的数据模型上进行OWL推理
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        inf = ModelFactory.createInfModel(reasoner, fusionModel);
    }

    public void closeModel() {
        ontologyModel.close();
        fusionModel.close();
    }

    public void write_origin_data_to_neo4j() {
        String ttlInsert = "CALL semantics.importRDF('file:///F:/IDEA/2019-XLab-KnowledgeReasoning/data/Ontology Reasoning/missing_data.ttl','Turtle', {shortenUrls: true})";
        neoDao.updateTriplesInNeo4j(ttlInsert);
    }

    public void write_ontology_reasoning_data_to_neo4j() {
        String ttlInsert = "CALL semantics.importRDF('file:///F:/IDEA/2019-XLab-KnowledgeReasoning/data/Ontology Reasoning/after_ontology_reasoning_for_neo4j.ttl','Turtle', {shortenUrls: true})";
        neoDao.updateTriplesInNeo4j(ttlInsert);
    }

    public void OntologyReasoning() throws FileNotFoundException {
        //本体推理代码（在整合到构建本体文件代码时移除）
        Model fusionModel = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open( "data/newOntology_fix_typo.ttl" );
        if (in == null) {
            throw new IllegalArgumentException(" not found");
        }

        fusionModel.read(in, "","TURTLE");

        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, fusionModel);

        Operations.outputAllTriples_to_ttl(infModel, "data/newOntology_after_reasoning.ttl");

        String ttlInsert = "CALL semantics.importRDF('file:///F:/IDEA/2019-XLab-KnowledgeReasoning/data/Ontology Reasoning/newOntology_after_reasoning.ttl','Turtle', {shortenUrls: true})";
        neoDao.updateTriplesInNeo4j(ttlInsert);
    }

    public static void main(String[] args) throws IOException {
//        //读入原始数据
//        readOriginData();
//
//        //将原始数据写入本地
//        outputOriginTriples();
//
//        //进行本体推理
//        OntologyReasoning();
//
//        //将推理后的数据写入本地
//        outputOntologyTriples();
//
//        closeModel();
    }
}
