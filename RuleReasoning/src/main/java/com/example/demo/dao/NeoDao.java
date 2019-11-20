package com.example.demo.dao;


import lombok.Data;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.*;


@Repository("NeoDao")
@Data public class NeoDao {

        private String ipAddress;
        private String user;
        private String password;
        private String ttlInsert;

        private String queryString;
        private String rule;

        private ResultSet resultSet;

        public NeoDao(){

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

                        ipAddress = dt.getElementsByTagName("neo4jIpAddress").item(0).getTextContent();
                        user = dt.getElementsByTagName("neo4jUser").item(0).getTextContent();
                        password = dt.getElementsByTagName("neo4jPassword").item(0).getTextContent();
                        queryString = dt.getElementsByTagName("neo4jQueryString").item(0).getTextContent();
                        rule = dt.getElementsByTagName("rule").item(0).getTextContent();
                        ttlInsert=dt.getElementsByTagName("ttlInsert").item(0).getTextContent();
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public ResultSet getTriples() {

                //Class.forName("org.neo4j.jdbc.Driver");

                try {
                        Connection connection = DriverManager.getConnection(ipAddress, user, password);
                        Statement statement = connection.createStatement();
                        resultSet = statement.executeQuery(queryString);

                } catch (Exception e) {
                        e.printStackTrace();
                }

                return resultSet;
        }

        public void updateTriplesInNeo4j() {

                try {
                        Connection connection = DriverManager.getConnection(ipAddress, user, password);
                        connection.createStatement().executeQuery(ttlInsert);

                } catch (Exception e) {
                        e.printStackTrace();
                }


        }
}




