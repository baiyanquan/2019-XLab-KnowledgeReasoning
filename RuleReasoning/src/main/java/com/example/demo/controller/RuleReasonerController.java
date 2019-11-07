package com.example.demo.controller;

import com.example.demo.service.RuleReasonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/reasoner"})
public class RuleReasonerController {


        @Autowired
        private RuleReasonerService ruleReasonerService;

        @RequestMapping(
                value = {"/start-rule-reason"},
                method = {RequestMethod.GET}
        )
        public String startRuleReason() {
            return this.ruleReasonerService.reason();
        }

    }
