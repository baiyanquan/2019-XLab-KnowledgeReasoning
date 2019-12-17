package com.tongji.knowledgereasoning.util;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;


/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
public class FusekiDao {

    public static ResultSet getTriples(){

        String queryAddress = "http://10.60.38.173:3030//DevKGData/query";
        String queryString = "SELECT DISTINCT ?s ?p ?o { ?s ?p ?o }";

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(queryAddress);

        Query query = QueryFactory.create(queryString);

        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(query);
            ResultSet rs = qExec.execSelect();
            conn.close();
            return rs;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateTriplesInFuseki(Model model) {
        String updateAddress = "http://10.60.38.173:3030//DevKGData/update";

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(updateAddress);

        try (RDFConnection conn = (RDFConnectionFuseki) builder.build()) {


            StmtIterator itr = model.listStatements();
            while (itr.hasNext()) {

                Statement statement = itr.nextStatement();

                String subject = statement.getSubject().toString();
                String predicate = statement.getPredicate().toString();
                String object = statement.getObject().toString();

                predicate = subject.substring(0, subject.indexOf("1")) + "10.60.38.181/" + predicate;
                String insert = "insert {<" + subject + "> <" + predicate + "> <" + object + ">} where {}";

                //System.out.println(insert);
                conn.update(insert);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
