package com.tongji.knowledgereasoning.util;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;



/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
public class Operations {
    public static void outputAllTriples(Model model) {
        System.out.println("output start...");
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement a = itr.nextStatement();
            if(a.getPredicate().toString().contains("provides")) {
                System.out.println(a);
            }
        }
        System.out.println("output end...");
    }

    public static void outputList(List<String> list) {
        for(String attribute : list) {
            System.out.println(attribute);
        }
    }

    public static void outputAllTriples_to_ttl(Model model, String filename) throws FileNotFoundException {
        model.write(new FileOutputStream(filename),"TURTLE");

    }
}