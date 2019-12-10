package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.OntologyReasonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: knowledgereasoning
 * @description: Ontology Reasoning Controller.
 * @author: Zhe Zhang
 * @create: 2019/12/10
 **/

@RestController
@RequestMapping({"/ontology-reasoner"})
public class OntologyReasonerController {

//    @Autowired
//    private OntologyReasonerService ontologyReasonerService;

//    @RequestMapping(value = "/start-fuseki-reason",method = RequestMethod.POST)
//    public String startFusekiReason(@RequestParam("rule")String rule){
//        return ruleReasonerService.fusekiReasoning(rule);
//    }
//
//    @RequestMapping(value = "/start-neo4j-reason",method = RequestMethod.POST)
//    public String startNeo4jReason(@RequestParam("rule")String rule) {
//        return ruleReasonerService.neo4jReasoning(rule);
//    }


}
