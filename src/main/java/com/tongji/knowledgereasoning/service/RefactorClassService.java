package com.tongji.knowledgereasoning.service;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/04
 **/
public class RefactorClassService {
    public static void main(String[] args) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        OntClass namespace = ontModel.createClass(":Namespace");
        OntClass pod = ontModel.createClass(":Pod");
        OntClass container = ontModel.createClass(":Container");
        OntClass service = ontModel.createClass(":Service");
        OntClass server = ontModel.createClass(":Server");
        OntClass environment = ontModel.createClass(":Environment");



        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter("data/parts/class_part.ttl");//没有文件会自动创建
        } catch (IOException e) {
            e.printStackTrace();
        }

        ontModel.write(fwriter,"TURTLE");
    }
}
