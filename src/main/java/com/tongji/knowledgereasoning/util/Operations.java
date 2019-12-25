package com.tongji.knowledgereasoning.util;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;


/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
public class Operations {
    public static Vector outputAllTriples(Model model) {
        Vector<String> result_vec = new Vector<>();

        System.out.println("output start...");
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement a = itr.nextStatement();
            if(a.getPredicate().toString().contains("provides")) {
                System.out.println(a);
                result_vec.add(a.toString());
            }
        }
        System.out.println("output end...");
        return result_vec;
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