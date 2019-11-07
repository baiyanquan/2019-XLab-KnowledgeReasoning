package com.example.demo.service;

import com.example.demo.dao.TripleDao;
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

@Service
public class RuleReasonerService {

    @Autowired
    private TripleDao tripleDao;

    //rule: (?X contains ?Y) (?X provides ?Z) -> (?Y supports ?Z)
    public String reason(){

        Model model = ModelFactory.createDefaultModel();
        ResultSet rs = tripleDao.getTriples();
        while (rs.hasNext()) {

            QuerySolution qs = rs.next() ;

            String subject = qs.get("s").toString();
            String object = qs.get("o").toString();
            String predicate = qs.get("p").toString();

            //(?X contains ?Y)
            if(predicate.contains("contains")) {
                model.add(model.createResource(subject), model.createProperty("contains"), model.createResource(object));
            }
            //(?X provides ?Z)
            else if(predicate.contains("provides")) {
                model.add(model.createResource(subject), model.createProperty("provides"), model.createResource(object));
            }
        }
        String userDefinedRules = tripleDao.getRule();
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(userDefinedRules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);

        //输出原数据
        outputAllTriples(model);
        //输出推理得出的数据
        outputAllTriples(inf.getDeductionsModel());

        return TriplesToJson(inf.getDeductionsModel());
    }

    private String TriplesToJson(Model model) {
        JSONArray triples = new JSONArray();
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            Statement nowStatement = itr.nextStatement();

            String subject = nowStatement.getSubject().toString();
            String predicate = nowStatement.getPredicate().toString();
            String object = nowStatement.getObject().toString();

            JSONObject triple = new JSONObject();

            triple.put("subject", subject);
            //set predicate
            predicate=subject.substring(0,subject.indexOf("1"))+"10.60.38.181/"+predicate;
            triple.put("predicate", predicate);
            triple.put("object", object);

            triples.put(triple);
        }
        return triples.toString();
    }

    private void outputAllTriples(Model model) {
        StmtIterator itr = model.listStatements();
        while (itr.hasNext()) {
            System.out.println(itr.nextStatement());
        }
    }

}
