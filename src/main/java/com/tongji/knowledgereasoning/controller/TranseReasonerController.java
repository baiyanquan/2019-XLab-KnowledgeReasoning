package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.TranseService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/transe-reasoner-test"})
public class TranseReasonerController {
    @Autowired
    private TranseService transeService;

    @RequestMapping(value = "/start-transe-reason", method = RequestMethod.GET)
    public String startTranseReason() {
        return transeService.TransEReasoning();
    }
}
