package com.tongji.knowledgereasoning.util;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.util.List;



/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
public class Operations {
    public static void outputAllTriples(Model model) {
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement a = itr.nextStatement();
            if(a.getPredicate().toString().contains("provides")) {
                System.out.println(a);
            }
        }
    }

    public static void outputList(List<String> list) {
        for(String attribute : list) {
            System.out.println(attribute);
        }
    }
}