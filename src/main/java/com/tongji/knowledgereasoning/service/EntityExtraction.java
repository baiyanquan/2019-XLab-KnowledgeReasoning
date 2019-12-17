package com.tongji.knowledgereasoning.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
//实体提取
public class EntityExtraction {

    //提取所有类型为type的运维实体
    public static List<String> entityFound(String type,Model model) {
        List<String> entity = new ArrayList<String>();

        StmtIterator iterator = model.listStatements();

        while (iterator.hasNext()) {

            Statement qs = iterator.nextStatement();

            RDFNode s = qs.getSubject();

            RDFNode o = qs.getObject();

            String subject = s.toString();

            String object = o.toString();

            String predicate = qs.getPredicate().toString();

            if (s.isResource() && subject.contains(type)) {
                entityAdd(entity, subject);
                //model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
            }
            if (o.isResource() && object.contains(type)) {
                entityAdd(entity, object);
            }
        }
        return entity;
    }

    //检查实体是否为运维实体对应的属性信息。检查方法：判断是否是最短的（因为最短的是派生出状态信息实体的实体）
    private static void entityAdd(List<String> entity, String element){
        if (!entity.contains(element)) {
            boolean add = true;
            for(int i=0;i<entity.size();i++){
                if(entity.get(i).contains(element)){
                    entity.set(i, element);
                    add = false;
                }
                else if(element.contains(entity.get(i))){
                    add = false;
                }
            }
            if(add){
                entity.add(element);
            }
        }
    }

    //找到列表中运维实体对应的状态信息实体
    public static List<String> entityProfileFound(List<String> entity, Model model){
        List<String> entityProfile = new ArrayList<String>();

        StmtIterator iterator = model.listStatements();

        while (iterator.hasNext()) {

            Statement qs = iterator.nextStatement();

            RDFNode s = qs.getSubject();

            RDFNode o = qs.getObject();

            String subject = s.toString();

            String object = o.toString();

            String predicate = qs.getPredicate().toString();

            for(String i:entity){
                String profile = i + "/profile";
                if(i.equals(subject) && predicate.contains("profile")) {
                    if (predicate.equals(profile)) {
                        if (!entityProfile.contains(object)) {
                            entityProfile.add(object);
                        }
                    }
                }
            }
        }
        return entityProfile;
    }
}
