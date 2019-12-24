package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.util.FusekiDao;
import com.tongji.knowledgereasoning.util.Operations;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.stereotype.Service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
@Service
public class MetadataLayerConstructService {
    private OntModel ontModel;

    private Map<String, OntClass> ontClassMap;

    private Map<String, OntProperty> ontPropertyMap;

    //初始化本体模型、本体Map、属性Map
    private void init(){
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontClassMap = new HashMap<>();
        ontPropertyMap = new HashMap<>();
    }

    public void refactorPrefix(){
        ontModel.setNsPrefix( "owl", "http://www.w3.org/2002/07/owl#" );
        ontModel.setNsPrefix( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
        ontModel.setNsPrefix( "rdfs", "http://www.w3.org/2000/01/rdf-schema#" );

        ontModel.setNsPrefix( "", "http://10.60.38.181/KGns/" );
        ontModel.setNsPrefix( "rel", "http://10.60.38.181/KGns/relationship/" );
        ontModel.setNsPrefix( "namespace_rel", "http://namespace/10.60.38.181/" );
        ontModel.setNsPrefix( "pods_rel", "http://pods/10.60.38.181/" );
        ontModel.setNsPrefix( "containers_rel", "http://containers/10.60.38.181/" );
        ontModel.setNsPrefix( "services_rel", "http://services/10.60.38.181/" );
        ontModel.setNsPrefix( "servers_rel", "http://servers/10.60.38.181/" );
        ontModel.setNsPrefix( "environment_rel", "http://environment/10.60.38.181/" );

        ontModel.setNsPrefix( "namespace", "http://10.60.38.181/KGns/Namespace/" );
        ontModel.setNsPrefix( "pods", "http://10.60.38.181/KGns/Pod/" );
        ontModel.setNsPrefix( "containers", "http://10.60.38.181/KGns/Container/" );
        ontModel.setNsPrefix( "services", "http://10.60.38.181/KGns/Service/" );
        ontModel.setNsPrefix( "environment", "http://10.60.38.181/KGns/Environment/" );
    }

    public void refactorClass(){
        ontClassMap.put("namespace", ontModel.createClass(":Namespace"));
        ontClassMap.put("pod", ontModel.createClass(":Pod"));
        ontClassMap.put("container", ontModel.createClass(":Container"));
        ontClassMap.put("service", ontModel.createClass(":Service"));
        ontClassMap.put("server", ontModel.createClass(":Server"));
        ontClassMap.put("environment", ontModel.createClass(":Environment"));
        ontClassMap.put("containerStatusInformation", ontModel.createClass(":ContainerStatusInformation"));
        ontClassMap.put("serviceStatusInformation", ontModel.createClass(":ServiceStatusInformation"));
    }

    public void refactorProperty(){
        OntProperty namespaceSupervises = ontModel.createObjectProperty("http://namespace/10.60.38.181/supervises");
        namespaceSupervises.addDomain(ontClassMap.get("namespace"));
        namespaceSupervises.addRange(ontClassMap.get("namespace"));
        ontPropertyMap.put("namespace-supervises", namespaceSupervises);

        OntProperty podContains = ontModel.createObjectProperty("http://pods/10.60.38.181/contains");
        podContains.addDomain(ontClassMap.get("pod"));
        podContains.addRange(ontClassMap.get("container"));
        ontPropertyMap.put("pod-contains", podContains);

        OntProperty podDeployedIn = ontModel.createObjectProperty("http://pods/10.60.38.181/deployed_in");
        podDeployedIn.addDomain(ontClassMap.get("pod"));
        podDeployedIn.addRange(ontClassMap.get("server"));
        ontPropertyMap.put("pod-deployed_in", podDeployedIn);

        OntProperty podProvides = ontModel.createObjectProperty("http://pods/10.60.38.181/provides");
        podProvides.addDomain(ontClassMap.get("pod"));
        podProvides.addRange(ontClassMap.get("service"));
        ontPropertyMap.put("pod-provides", podProvides);

        OntProperty containerProfile = ontModel.createObjectProperty("http://containers/10.60.38.181/profile");
        containerProfile.addDomain(ontClassMap.get("container"));
        containerProfile.addRange(ontClassMap.get("containerStatusInformation"));
        ontPropertyMap.put("container-profile", containerProfile);

        OntProperty serviceProfile = ontModel.createObjectProperty("http://services/10.60.38.181/profile");
        serviceProfile.addDomain(ontClassMap.get("service"));
        serviceProfile.addRange(ontClassMap.get("serviceStatusInformation"));
        ontPropertyMap.put("service-profile", serviceProfile);

        OntProperty serverManage = ontModel.createObjectProperty("http://servers/10.60.38.181/manage");
        serverManage.addDomain(ontClassMap.get("server"));
        serverManage.addRange(ontClassMap.get("server"));
        ontPropertyMap.put("server-manage", serverManage);

        OntProperty environmentHas = ontModel.createObjectProperty("http://environment/10.60.38.181/has");
        environmentHas.addDomain(ontClassMap.get("environment"));
        environmentHas.addRange(ontClassMap.get("server"));
        ontPropertyMap.put("environment-has", environmentHas);
    }

    public void MetadataLayerConstruct(){
        System.out.println("begin...");

        ResultSet rs = FusekiDao.getTriples();
        //用于存储从数据库读出的数据
        Model model = ModelFactory.createDefaultModel();
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            String subject = qs.get("s").toString();
            RDFNode o = qs.get("o");
            String p = qs.get("p").toString();
            if(o.isResource()){
                model.add(model.createResource(subject), model.createProperty(p), model.createResource(o.toString()));
            }
            else {
                model.add(model.createResource(subject), model.createProperty(p), model.createLiteral(o.toString()));
            }
        }

        //实体目前暂时分为两类：一类为运维实体，即entityType中列出的部分，一类为这些运维实体对应的状态信息，即StatusInformation
        String [] entityType = {"namespace", "pod", "container", "service", "server", "environment"};
        Map<String, String> classStatusMap = new HashMap<>();
        for(String i:entityType) {
            classStatusMap.put(i, i + "StatusInformation");
        }

        //设定各实体与关系的对应map
        Map<String, String[]> relationTypeMap = new HashMap<>();
        relationTypeMap.put("namespace", new String[]{"supervises"});
        relationTypeMap.put("pod", new String[]{"contains", "deployed_in", "provides"});
        relationTypeMap.put("container", new String[]{"profile"});
        relationTypeMap.put("service", new String[]{"profile"});
        relationTypeMap.put("server", new String[]{"manage"});
        relationTypeMap.put("environment", new String[]{"has"});

        init();
        refactorPrefix();
        refactorClass();
        refactorProperty();

        //对每种实体轮流处理
        for(String className:relationTypeMap.keySet()){
            //找到所有的此类别中的运维实体
            List<String> entity = EntityExtraction.entityFound(className, model);

            for(String i:entity){
                ontClassMap.get(className).createIndividual(i);
            }

            //找到所有上述运维实体对应的状态信息实体
            List<String> entityProperty = EntityExtraction.entityProfileFound(entity, model);
            for(String i:entityProperty){
                ontClassMap.get(classStatusMap.get(className)).createIndividual(i);
            }

            //将此类别中的关系归属到一个统一命名的关系下（例：所有不同前缀的contains都是特定前缀的contains的subProperty）
            for(String property: relationTypeMap.get(className)){
                List<String> predicateList = PredicateExtraction.propertyFound(entity, property, model);
                for(String i:predicateList){
                    OntProperty predicate = ontModel.createObjectProperty(i);
                    OntProperty a = ontPropertyMap.get(className + '-' + property);
                    ontPropertyMap.get(className + '-' + property).addSubProperty(predicate);
                }
            }
        }

        //本体推理代码（在整合到构建本体文件代码时移除）
        Model fusionModel = ModelFactory.createDefaultModel();
        fusionModel.add(ontModel);
        fusionModel.add(model);
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, fusionModel);
        //Operations.outputAllTriples(infModel);

        System.out.println("\n");
        //写入文件
//        try {
//            FileWriter fwriter = new FileWriter("C:/Users/Administrator/Desktop/newOntology.ttl");     //没有文件会自动创建
//            ontModel.write(fwriter,"TURTLE");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("end...");
    }

    public static void main(String[] args) {

    }
}
