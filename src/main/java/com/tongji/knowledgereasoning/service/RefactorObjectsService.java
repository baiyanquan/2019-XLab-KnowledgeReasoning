package com.tongji.knowledgereasoning.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

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
public class RefactorObjectsService {
    public static void main(String[] args) {
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
            File file = new File("data/parts/objects_part.ttl");
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getName(), true);
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

//            Collections.sort(buf_vec);

            for (String s : buf_vec) {
                bufferedWriter.write(s);
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
