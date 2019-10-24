package com.doublez.jenastudy.Service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.system.Txn;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: jenastudy
 * @description: This is a program for testing Fuseki & connect to the Service in the x-lab
 * @author: doubleZ
 * @create: 2019/10/22
 **/
public class FusekiTest {

    private static void outputAllTriples(Model model) {
        /**
         * @description: 遍历model，打印所有属性
         *
         * @param model : 模型
         *
         * @return : void
         **/
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement stmt      = itr.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.println("====================================");
            System.out.println("Statement: " + stmt.toString());
            System.out.print("Subject: " + subject.toString());
            System.out.print("Predicate: " + predicate.toString());
            System.out.print("Object: ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println("====================================");
            System.out.println("");
        }
    }

    private static void outputList(List<String> list) {
        for(String attribute : list) {
            System.out.println(attribute);
        }
    }

//    private static void updateTriplesInFuseki(Model model) {
//        StmtIterator itr = model.listStatements();
//        RDFConnectionRemoteBuilder myBuilder = RDFConnectionFuseki.create().destination("http://localhost:3030//Data/update");
//        try (RDFConnection conn = (RDFConnectionFuseki)myBuilder.build() ){
//            while (itr.hasNext()) {
//                Statement nowStatement = itr.nextStatement();
//                String subject = nowStatement.getSubject().toString();
//                String predicate = nowStatement.getPredicate().toString();
//                String object = nowStatement.getObject().toString();
//                String sentence = "insert {<" + subject + "> <" + subject + "/" + predicate +"> <" + object + ">} where {}";
//                System.out.println(sentence);
//                conn.update(sentence);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://10.60.38.173:3030//DevKGData/query");

        Query query = QueryFactory.create("SELECT DISTINCT ?s ?p ?o { ?s ?p ?o }");

        String userDefinedRules = "[rule1: (?X contains ?Y) (?X provides ?Z) -> (?Y supports ?Z)] ";

        List<String> container = new ArrayList<String>();

        List<String> service = new ArrayList<String>();

        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(query);

            ResultSet rs = qExec.execSelect();

            while (rs.hasNext()) {

                QuerySolution qs = rs.next() ;

                String subject = qs.get("s").toString();

                String object = qs.get("o").toString();

                String predicate = qs.get("p").toString();

//                if(predicate.contains("contains") || predicate.contains("provides")) {
//                    model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
//                }



                if(predicate.contains("contains")) {
                    model.add(model.createResource(subject), model.createProperty("contains"), model.createResource(object));
                    if(object.contains("containers")){
                        if(!container.contains(object)){
                            container.add(object);
                        }
                    }
                    else if(object.contains("services")){
                        if(!service.contains(object)){
                            service.add(object);
                        }
                    }
                }
                else if(predicate.contains("provides")) {
                    model.add(model.createResource(subject), model.createProperty("provides"), model.createResource(object));
                    if(object.contains("containers")){
                        if(!container.contains(object)){
                            container.add(object);
                        }
                    }
                    else if(object.contains("services")){
                        if(!service.contains(object)){
                            service.add(object);
                        }
                    }
                }
                outputAllTriples(model);
            }
            qExec.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Containers：");
        outputList(container);

        System.out.println("Services：");
        outputList(service);

        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(userDefinedRules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        System.out.println("推理结果：");
        outputAllTriples(inf.getDeductionsModel());
        //updateTriplesInFuseki(inf.getDeductionsModel());
    }
}
