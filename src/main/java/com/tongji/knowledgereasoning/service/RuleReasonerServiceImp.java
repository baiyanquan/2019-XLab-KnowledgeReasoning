package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.FusekiDao;
import com.tongji.knowledgereasoning.dao.NeoDao;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service("RuleReasonerService")
public class RuleReasonerServiceImp implements RuleReasonerService {

    @Autowired
    private FusekiDao fusekiDao;
    @Autowired
    private NeoDao neoDao;

    FileWriter ruleReasonResult = null;


    public String fusekiReasoning(String rule) {

        Model model = ModelFactory.createDefaultModel();
        ResultSet rs = fusekiDao.getTriples();
        while (rs.hasNext()) {

            QuerySolution qs = rs.next();

            String subject = qs.get("s").toString();
            String object = qs.get("o").toString();
            String predicate = qs.get("p").toString();

            String[] p = predicate.split("/");
            predicate = p[p.length - 1];
            //System.out.println(predicate);

            model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));

        }

        Model modelAfterReason = reasoner(model, rule);
        String triples = TriplesToJson(modelAfterReason);

        //输出原数据
        //outputAllTriples(model);
        //输出推理得出的数据
        //outputAllTriples(modelAfterReason);

        //写回数据库
        fusekiDao.updateTriplesInFuseki(modelAfterReason);
        return triples;
    }

    public String neo4jReasoning(String rule) {

        Model model = ModelFactory.createDefaultModel();

        java.sql.ResultSet rs = neoDao.getTriples();

        try {
            while (rs.next()) {
                String subject = rs.getString(1);
                String predicate = rs.getString(2);
                String object = rs.getString(3);

                String[] p = predicate.split("__");
                predicate = p[p.length - 1];
                //System.out.println(predicate);
                //System.out.println(predicate);
                model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
            }
            //writeToFile(model,"data/neo4jdata.ttl");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Model modelAfterReason = reasoner(model, rule);
        String triples = TriplesToJson(modelAfterReason);
        String ttlInsert = "CALL semantics.importRDF('file:///F:/IDEA/2019-XLab-KnowledgeReasoning/data/RuleReasoning/RuleReasonResult.ttl','Turtle', {shortenUrls: true})";
        neoDao.updateTriplesInNeo4j(ttlInsert);
        return triples;
    }

    private Model reasoner(Model model, String rule) {
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rule));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        return inf.getDeductionsModel();
    }

    /*
        private Model modelAfterReason(Model model){

            StmtIterator itr = model.listStatements();
            Model modelAfterReason = ModelFactory.createDefaultModel();
            while (itr.hasNext()) {
                Statement nowStatement = itr.nextStatement();

                String subject = nowStatement.getSubject().toString();
                String predicate = nowStatement.getPredicate().toString();
                String object = nowStatement.getObject().toString();

                //set predicate
                predicate=subject.substring(0,subject.indexOf("1"))+"10.60.38.181/"+predicate;

                modelAfterReason.add(modelAfterReason.createResource(subject),
                        modelAfterReason.createProperty(predicate),
                        modelAfterReason.createResource(object));
                writeToFile(modelAfterReason,"data/RuleReasoning/RuleReasonResult.ttl");
            }
            return modelAfterReason;
        }
     */
    private String TriplesToJson(Model model) {
        JSONArray triples = new JSONArray();
        StmtIterator itr = model.listStatements();
        Model modelAfterReason = ModelFactory.createDefaultModel();
        while (itr.hasNext()) {
            Statement nowStatement = itr.nextStatement();

            String subject = nowStatement.getSubject().toString();
            String predicate = nowStatement.getPredicate().toString();
            String object = nowStatement.getObject().toString();

            JSONObject triple = new JSONObject();

            //set predicate
            predicate = subject.substring(0, subject.indexOf("1")) + "10.60.38.181/" + predicate;
            triple.put("subject", subject);
            triple.put("predicate", predicate);
            triple.put("object", object);

            triples.put(triple);

            modelAfterReason.add(modelAfterReason.createResource(subject),
                    modelAfterReason.createProperty(predicate),
                    modelAfterReason.createResource(object));
            writeToFile(modelAfterReason, "data/RuleReasoning/RuleReasonResult.ttl");
        }
        return triples.toString();
    }

    private void outputAllTriples(Model model) {
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            System.out.println(itr.nextStatement());
        }
    }

    private void writeToFile(Model model, String filepath) {

        try {
            ruleReasonResult = new FileWriter(filepath);//没有文件会自动创建

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.write(ruleReasonResult, "TURTLE");    //写入文件,默认是xml方式,可以自己指定

    }
}
