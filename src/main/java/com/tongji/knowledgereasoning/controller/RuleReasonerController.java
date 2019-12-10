package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.RuleReasonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/rule-reasoner"})
public class RuleReasonerController {

    @Autowired
    private RuleReasonerService ruleReasonerService;

    @RequestMapping(value = "/start-fuseki-reason",method = RequestMethod.POST)
    public String startFusekiReason(@RequestParam("rule")String rule){
        return ruleReasonerService.fusekiReasoning(rule);
    }

    @RequestMapping(value = "/start-neo4j-reason",method = RequestMethod.POST)
    public String startNeo4jReason(@RequestParam("rule")String rule) {
        return ruleReasonerService.neo4jReasoning(rule);
    }

}
