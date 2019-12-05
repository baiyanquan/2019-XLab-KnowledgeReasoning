package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.LabDao;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/04
 **/
@Service("RefactorDataService")
public class RefactorDataService {

    @Autowired
    private static LabDao labDao;

    private static OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    private static OntClass namespace;
    private static OntClass pod;
    private static OntClass container;
    private static OntClass service;
    private static OntClass server;
    private static OntClass environment;

    private static FileWriter fwriter = null;
    private static String url = "data/refactor_data.ttl";

    public static void refactor_prefix(){
        ontModel.setNsPrefix( "owl", "http://www.w3.org/2002/07/owl#" );
        ontModel.setNsPrefix( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
        ontModel.setNsPrefix( "rdfs", "http://www.w3.org/2000/01/rdf-schema#" );

        ontModel.setNsPrefix( "", "http://localhost/KGns/" );
        ontModel.setNsPrefix( "rel", "http://localhost/KGns/relationship/" );
        ontModel.setNsPrefix( "namespace_rel", "http://namespace/10.60.38.181/" );
        ontModel.setNsPrefix( "pods_rel", "http://pods/10.60.38.181/" );
        ontModel.setNsPrefix( "containers_rel", "http://containers/10.60.38.181/" );
        ontModel.setNsPrefix( "services_rel", "http://services/10.60.38.181/" );
        ontModel.setNsPrefix( "servers_rel", "http://servers/10.60.38.181/" );
        ontModel.setNsPrefix( "environment_rel", "http://environment/10.60.38.181/" );

        ontModel.setNsPrefix( "namespace", "http://localhost/KGns/Namespace/" );
        ontModel.setNsPrefix( "pods", "http://localhost/KGns/Pod/" );
        ontModel.setNsPrefix( "containers", "http://localhost/KGns/Container/" );
        ontModel.setNsPrefix( "services", "http://localhost/KGns/Service/" );
        ontModel.setNsPrefix( "environment", "http://localhost/KGns/Environment/" );
    }

    public static void refactor_class(){
        namespace = ontModel.createClass(":Namespace");
        pod = ontModel.createClass(":Pod");
        container = ontModel.createClass(":Container");
        service = ontModel.createClass(":Service");
        server = ontModel.createClass(":Server");
        environment = ontModel.createClass(":Environment");
    }

    public static void refactor_property(){
        OntProperty supervises = ontModel.createObjectProperty("http://namespace/10.60.38.181/supervises");
        supervises.addDomain(namespace);
        supervises.addRange(namespace);

        OntProperty contains = ontModel.createObjectProperty("http://pods/10.60.38.181/contains");
        contains.addDomain(pod);
        contains.addRange(container);

        OntProperty deployed_in = ontModel.createObjectProperty("http://pods/10.60.38.181/deployed_in");
        deployed_in.addDomain(pod);
        deployed_in.addRange(server);

        OntProperty provides = ontModel.createObjectProperty("http://pods/10.60.38.181/provides");
        provides.addDomain(pod);
        provides.addRange(service);

        OntProperty cc_profile = ontModel.createObjectProperty("http://containers/10.60.38.181/cc_profile");
        cc_profile.addDomain(container);
        cc_profile.addRange(container);

        OntProperty ss_profile = ontModel.createObjectProperty("http://services/10.60.38.181/ss_profile");
        ss_profile.addDomain(service);
        ss_profile.addRange(service);

        OntProperty manage = ontModel.createObjectProperty("http://servers/10.60.38.181/manage");
        manage.addDomain(server);
        manage.addRange(server);

        OntProperty has = ontModel.createObjectProperty("http://environment/10.60.38.181/has");
        has.addDomain(environment);
        has.addRange(server);
    }

    public static void refactor_object(){
        Model model = ModelFactory.createDefaultModel();

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://10.60.38.173:3030//DevKGData/query");

        Query query = QueryFactory.create("SELECT DISTINCT ?s ?p ?o { ?s ?p ?o }");

        List<String> class_=new ArrayList<>();
        class_.add("http://namespace/10.60.38.181");
        class_.add("http://pods/10.60.38.181");
        class_.add("http://services/10.60.38.181");
        class_.add("http://containers/10.60.38.181");
        class_.add("http://server/10.60.38.181");
        class_.add("http://environment/10.60.38.181");

        HashSet<String> object_set = new HashSet<String>();

        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(query);

            ResultSet rs = qExec.execSelect();

            while (rs.hasNext()) {

                QuerySolution qs = rs.next() ;

                String subject = qs.get("s").toString();
                String object = qs.get("o").toString();
                String predicate = qs.get("p").toString();

                for(String s:class_) {

                    if (subject.contains(s)) {

                        String[] p = predicate.split("/");
                        String predicate_ = s + '/' + p[p.length - 1];

                        if(!object_set.contains(subject)){
                            object_set.add(subject);
                        }
                        if(!object_set.contains(object)){
                            object_set.add(object);
                        }

                        model.add(model.createResource(subject), model.createProperty(predicate_), model.createResource(object));

                    }
                }

            }
            qExec.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        try{
            File file = new File(url);

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Vector<String> buf_vec = new Vector<String>();


            for (String s : object_set) {
                try{
                    if(s.substring(0,7).equals("http://")){
                        String []buf = s.split("/", 4);
                        String type = buf[2];
                        switch (type){
                            case "server":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Server" + '\n');
                                break;
                            case "containers":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Container" + '\n');
                                break;
                            case "pods":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Pod" + '\n');
                                break;
                            case "services":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Service" + '\n');
                                break;
                            case "namespace":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Namespace" + '\n');
                                break;
                            case "environment":
                                buf_vec.add("<" + s + ">" + " rdf:type " + ":Environment" + '\n');
                                break;
                            default:
                                break;
                        }
                    }
                }catch (StringIndexOutOfBoundsException e){

                }
            }

            for (String s : buf_vec) {
                bufferedWriter.write(s);
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refactor_relation(){


        Model model = ModelFactory.createDefaultModel();
        ResultSet rs = labDao.getTriples();

        String namespace = "http://namespace/10.60.38.181";
        String pods = "http://pods/10.60.38.181";
        String services = "http://services/10.60.38.181";
        String containers = "http://containers/10.60.38.181";
        String server = "http://server/10.60.38.181";
        String environment = "http://environment/10.60.38.181";

        while (rs.hasNext()) {

            QuerySolution qs1 = rs.next();
            String subject = qs1.get("s").toString();
            String object = qs1.get("o").toString();
            String predicate = qs1.get("p").toString();

            if ((subject.contains(namespace) || subject.contains(pods) || subject.contains(containers) ||
                    subject.contains(services) || subject.contains(server) || subject.contains(environment))
                    && (object.contains(namespace) || object.contains(server) || object.contains(pods) ||
                    object.contains(services) || object.contains(containers) || object.contains(environment))) {

                String[] p = predicate.split("/");
                String predicate_ = subject.substring(0, subject.indexOf("1")) + "10.60.38.181/" + p[p.length - 1];

                model.add(model.createResource(subject), model.createProperty(predicate_), model.createResource(object));

            }
        }

        FileWriter fwriter = null;

        try {
            fwriter = new FileWriter("data/labdata.ttl");//没有文件会自动创建
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.write(fwriter,"TURTLE");	//写入文件,默认是xml方式,可以自己指定

    }

    public static void main(String[] args) {
        refactor_prefix();
        refactor_class();
        refactor_property();

        try {
            fwriter = new FileWriter(url);     //没有文件会自动创建
        } catch (IOException e) {
            e.printStackTrace();
        }

        ontModel.write(fwriter,"TURTLE");

        //TODO: 想法是先把之前的model写下来，再在ttl后追加object和relation
        refactor_object();
        refactor_relation();
    }
}
