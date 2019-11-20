package com.example.demo.controller;

import com.example.demo.service.RuleReasonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/rule-reasoner"})
public class RuleReasonerController {

    @Autowired
    private RuleReasonerService ruleReasonerService;


    @RequestMapping(value = "/start-fuseki-reason",method = RequestMethod.GET)
    public String startFusekiReason(){
        return ruleReasonerService.fusekiReasoning();
    }

    @RequestMapping(value = "/start-neo4j-reason",method = RequestMethod.GET)
    public String startNeo4jReason() {
        return ruleReasonerService.neo4jReasoning();
    }

}
