package com.tongji.knowledgereasoning.dao;

import lombok.Data;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.springframework.stereotype.Repository;

@Repository("LabDao")
@Data public class LabDao {

    private static ResultSet resultSet;

    public static ResultSet getTriples(){

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://10.60.38.173:3030//DevKGData/query");

        Query query= QueryFactory.create("SELECT ?s ?p ?o WHERE{ ?s ?p ?o }");
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {

            QueryExecution qExec = conn.query(query);
             resultSet = qExec.execSelect();

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

}
