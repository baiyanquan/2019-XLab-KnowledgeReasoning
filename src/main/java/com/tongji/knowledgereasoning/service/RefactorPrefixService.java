package com.tongji.knowledgereasoning.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/04
 **/
public class RefactorPrefixService {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();

        model.setNsPrefix( "owl", "http://www.w3.org/2002/07/owl#" );
        model.setNsPrefix( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
        model.setNsPrefix( "rdfs", "http://www.w3.org/2000/01/rdf-schema#" );

        model.setNsPrefix( "", "http://localhost/KGns/" );
        model.setNsPrefix( "rel", "http://localhost/KGns/relationship/" );
        model.setNsPrefix( "namespace_rel", "http://namespace/10.60.38.181/" );
        model.setNsPrefix( "pods_rel", "http://pods/10.60.38.181/" );
        model.setNsPrefix( "containers_rel", "http://containers/10.60.38.181/" );
        model.setNsPrefix( "services_rel", "http://services/10.60.38.181/" );
        model.setNsPrefix( "servers_rel", "http://servers/10.60.38.181/" );
        model.setNsPrefix( "environment_rel", "http://environment/10.60.38.181/" );

        model.setNsPrefix( "namespace", "http://localhost/KGns/Namespace/" );
        model.setNsPrefix( "pods", "http://localhost/KGns/Pod/" );
        model.setNsPrefix( "containers", "http://localhost/KGns/Container/" );
        model.setNsPrefix( "services", "http://localhost/KGns/Service/" );
        model.setNsPrefix( "environment", "http://localhost/KGns/Environment/" );


        try {
            model.write(new FileOutputStream("data/parts/prefix_part.ttl"),"TURTLE");      //write to a file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
