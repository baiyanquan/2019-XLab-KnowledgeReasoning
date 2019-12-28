package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.OntologyReasonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @program: knowledgereasoning
 * @description: Ontology Reasoning Controller.
 * @author: Zhe Zhang
 * @create: 2019/12/10
 **/

@RestController
@RequestMapping({"/ontology-reasoner-test"})
public class OntologyReasonerController {

    @Autowired
    private OntologyReasonerService ontologyReasonerService;

    @RequestMapping(value = "/start-ontology-reason", method = RequestMethod.GET)
    public void startOntologyReason() throws IOException {
        ontologyReasonerService.OntologyReasoning();

    }


}
