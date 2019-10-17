package com.example.demo.Dao;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class fusekiTestDaoImp implements fusekiTestDao{

   public ResultSet queryFuseki(){

       RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://10.60.38.173:3030//DevKGData/query");
       Query query = QueryFactory.create("SELECT ?s ?p?o\n" +
               "WHERE {\n" +
               "  ?s ?p ?o\n" +
               "}\n" +
               "LIMIT 1000");

       RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build();
       QueryExecution qExec = conn.query(query);
       ResultSet resultSet = qExec.execSelect();

      // qExec.close();
       return resultSet;
    }

}
