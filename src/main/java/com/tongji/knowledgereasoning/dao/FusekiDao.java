package com.tongji.knowledgereasoning.dao;

import lombok.Data;
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
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


@Repository("FusekiDao")
@Data public class FusekiDao {

    private String queryAddress;
    private String updateAddress;
    private String queryString;
    private String rule;

    public FusekiDao(){

        File f = new File("./info.xml");

        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = null;
        try {
            // 返回documentBuilderFactory对象
            dbf = DocumentBuilderFactory.newInstance();
            // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
            db = dbf.newDocumentBuilder();

            // 得到一个DOM并返回给document对象
            Document dt = db.parse(f);

            queryAddress = dt.getElementsByTagName("fusekiQueryAddress").item(0).getTextContent();
            updateAddress = dt.getElementsByTagName("fusekiUpdateAddress").item(0).getTextContent();
            queryString = dt.getElementsByTagName("fusekiQueryString").item(0).getTextContent();
            rule = dt.getElementsByTagName("rule").item(0).getTextContent();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTriples(){

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(queryAddress);

        //SELECT DISTINCT ?s ?p ?o { ?s ?p ?o }
        Query query = QueryFactory.create(queryString);

        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(query);
            ResultSet rs = qExec.execSelect();

            return rs;
        }
    }

    public void updateTriplesInFuseki(Model model) {

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
