package com.tongji.knowledgereasoning.service;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/04
 **/
public class RefactorPropertyService {
    public static void main(String[] args) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        OntClass namespace = ontModel.createClass(":Namespace");
        OntClass pod = ontModel.createClass(":Pod");
        OntClass container = ontModel.createClass(":Container");
        OntClass service = ontModel.createClass(":Service");
        OntClass server = ontModel.createClass(":Server");
        OntClass environment = ontModel.createClass(":Environment");

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

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter("data/parts/property_part.ttl");//没有文件会自动创建
        } catch (IOException e) {
            e.printStackTrace();
        }

        ontModel.write(fwriter,"TURTLE");
    }
}
