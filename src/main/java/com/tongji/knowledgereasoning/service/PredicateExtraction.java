package com.tongji.knowledgereasoning.service;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/

//谓词提取
public class PredicateExtraction {
    //提取关系为p、前缀不同的所有同一种类的谓词
    public static List<String> propertyFound(List<String> entity, String p, Model model){
        List<String> profileList = new ArrayList<String>();

        StmtIterator iterator = model.listStatements();

        while (iterator.hasNext()) {

            Statement qs = iterator.nextStatement();

            RDFNode s = qs.getSubject();

            RDFNode o = qs.getObject();

            String subject = s.toString();

            String object = o.toString();

            String predicate = qs.getPredicate().toString();

            for(String i:entity){
                if (i.equals(subject) && predicate.equals(i + "/" + p)) {
                    if (!profileList.contains(predicate)){
                        profileList.add(predicate);
                    }
                }
            }
        }
        return profileList;
    }
}

