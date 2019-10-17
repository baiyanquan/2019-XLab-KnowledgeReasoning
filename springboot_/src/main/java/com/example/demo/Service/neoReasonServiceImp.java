package com.example.demo.Service;

import com.example.demo.Dao.neoReasonRepository;
import com.example.demo.Model.TripleModel;
import com.example.demo.Model.fsRelation;
import com.example.demo.Model.msRelation;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("neoReason")
public class neoReasonServiceImp implements neoReasonService {

    @Autowired
    private neoReasonRepository nRR;

    public List<TripleModel> test(){

        List<fsRelation>fAndS=nRR.fAndS();
        List<msRelation>mAndS=nRR.mAndS();
        Model model = ModelFactory.createDefaultModel();

        String reasonRules = "[rule1: (?X father ?Z) (?Y mother ?Z) -> (?X married ?Y)] ";

        for(fsRelation it:fAndS) {
            String subject=it.getStartNode().getName();
            String object=it.getEndNode().getName();
            model.add(model.createResource(subject), model.createProperty("father"), model.createResource(object));
        }

        for(msRelation it:mAndS) {
            String subject=it.getStartNode().getName();
            String object=it.getEndNode().getName();
            model.add(model.createResource(subject), model.createProperty("mother"), model.createResource(object));
        }

        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(reasonRules));
        reasoner.setDerivationLogging(true);
        InfModel inf = ModelFactory.createInfModel(reasoner, model);
        Model m=inf.getDeductionsModel();

        List<TripleModel>list=new ArrayList<>();
        TripleModel tripleModel=new TripleModel();
        StmtIterator itr = m.listStatements();
        while (itr.hasNext()) {
            Statement Stmt=itr.nextStatement();
            tripleModel.setSubject(Stmt.getSubject().toString());
            tripleModel.setPredicate(Stmt.getPredicate().toString());
            tripleModel.setObject(Stmt.getObject().toString());

            list.add(tripleModel);
        }
        return list;
    }

}
