package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.NeoDao;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/

@Service
public class EventService {

    @Autowired
    private NeoDao neoDao = new NeoDao();

    public List<String> eventQuery(String service) {

        Model model = ModelFactory.createDefaultModel();
        java.sql.ResultSet rs = neoDao.getTriples();

        try {
            while (rs.next()) {
                String subject = rs.getString(1);
                String predicate = rs.getString(2);
                String object = rs.getString(3);

                if(predicate.equals("ns12__contains")){
                    predicate = "http://pods/10.60.38.181/contains";
                }else if(predicate.equals("ns12__deployed_in")){
                    predicate = "http://pods/10.60.38.181/deployed_in";
                }else if(predicate.equals("ns12__provides")){
                    predicate = "http://pods/10.60.38.181/provides";
                }else if(predicate.equals("ns13__profile")){
                    predicate = "http://services/10.60.38.181/profile";
                }else if(predicate.equals("ns14__profile")){
                    predicate = "http://containers/10.60.38.181/profile";
                }else if(predicate.equals("ns15__has")){
                    predicate = "http://environment/10.60.38.181/has";
                }else if(predicate.equals("ns16__manage")){
                    predicate = "http://servers/10.60.38.181/manage";
                }else if(predicate.equals("ns17__supervises")){
                    predicate = "http://namespace/10.60.38.181/supervises";
                }else if(predicate.equals("rdfs__domain")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#domain";
                }else if(predicate.equals("rdfs__range")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#range";
                }else if(predicate.equals("rdfs__subClassOf")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#subClassOf";
                }else if(predicate.equals("rdfs__subPropertyOf")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#subPropertyOf";
                }else if(predicate.equals("ns18__inject")){
                    predicate ="http://event/10.60.38.181/inject";
                }

                model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        String rules ="[rule1: (?X <http://pods/10.60.38.181/provides> ?Y) -> (?X <http://10.60.38.181/KGns/relates> ?Y)]\n" +
                "[rule2: (?X <http://10.60.38.181/KGns/relates> ?Y) (?X <http://pods/10.60.38.181/deployed_in> ?Z) -> (?Z <http://10.60.38.181/KGns/relates> ?Y)]\n" +
                "[rule3: (?X <http://10.60.38.181/KGns/relates> ?Y) (?X <http://pods/10.60.38.181/contains> ?Z) -> (?Z <http://10.60.38.181/KGns/relates> ?Y)]\n" +
                "[rule4: (?Y <http://10.60.38.181/KGns/relates> ?X) (?Z <http://event/10.60.38.181/inject> ?Y) -> (?Z <http://10.60.38.181/KGns/effects> ?X)]";

        Reasoner rulereasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(rulereasoner, infModel);
        Model modelAfterReason = inf.getDeductionsModel();
        List<String> events = new ArrayList<>();
        StmtIterator itr = modelAfterReason.listStatements();

        while (itr.hasNext()) {
            Statement nowStatement = itr.nextStatement();

            String subject = nowStatement.getSubject().toString();
            String predicate = nowStatement.getPredicate().toString();
            String object = nowStatement.getObject().toString();

            if (predicate.contains("http://10.60.38.181/KGns/effects") && object.contains(service)) {
                events.add(subject);
            }

        }
        return events;

    }

}
