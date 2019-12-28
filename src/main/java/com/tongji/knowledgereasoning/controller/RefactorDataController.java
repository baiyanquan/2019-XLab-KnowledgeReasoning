package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.RefactorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/refactor-data-test"})
public class RefactorDataController {
    @Autowired
    private RefactorDataService refactorDataService;

    @RequestMapping(method = RequestMethod.PUT)
    public void refactorData() {
        refactorDataService.refactorData();
    }
}
