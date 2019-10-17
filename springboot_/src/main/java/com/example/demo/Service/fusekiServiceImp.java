package com.example.demo.Service;

import com.example.demo.Dao.fusekiTestDao;
import com.example.demo.Model.TripleModel;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fusekiService")
public class fusekiServiceImp implements fusekiService {

    @Autowired
    private fusekiTestDao fusekiDao;

    public List<TripleModel> queryList() {

        ResultSet resultSet=fusekiDao.queryFuseki();
        TripleModel tripleModel=new TripleModel();
        List<TripleModel>list=new ArrayList<>();
        while (resultSet.hasNext()) {

            QuerySolution querySolution = resultSet.next();
            String subject= querySolution.get("s").toString();
            String object = querySolution.get("o").toString();
            String predicate = querySolution.get("p").toString();

            tripleModel.setSubject(subject);
            tripleModel.setPredicate(predicate);
            tripleModel.setObject(object);
            list.add(tripleModel);
        }
        return list;
    }

   /* public List<TripleModel> reasonTest(){

        ResultSet resultSet=fusekiDao.queryFuseki();
        Model model = ModelFactory.createDefaultModel();

        String reasonRules = "[rule1: (?X contains ?Y) (?X provides ?Z) -> (?Y supports ?Z)] ";

        while (resultSet.hasNext()) {

            QuerySolution querySolution = resultSet.next();
            String subject= querySolution.get("s").toString();
            String object = querySolution.get("o").toString();
            String predicate = querySolution.get("p").toString();
           // model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));

            if(predicate.contains("contains")) {
                model.add(model.createResource(subject), model.createProperty("contains"), model.createResource(object));

            } else if(predicate.contains("provides")) {
                model.add(model.createResource(subject), model.createProperty("provides"), model.createResource(object));
            }
        }

        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(reasonRules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);

        List<TripleModel>list=new ArrayList<>();
        TripleModel tripleModel=new TripleModel();
        Model m=inf.getDeductionsModel();
        StmtIterator itr = m.listStatements();

        while (itr.hasNext()) {
            Statement Stmt=itr.nextStatement();
            tripleModel.setSubject(Stmt.getSubject().toString());
            tripleModel.setPredicate(Stmt.getPredicate().toString());
            tripleModel.setObject(Stmt.getObject().toString());

            list.add(tripleModel);
        }
        return list;
    }*/

}
