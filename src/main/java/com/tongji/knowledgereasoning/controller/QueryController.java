package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.QueryService;
import com.tongji.knowledgereasoning.service.RuleReasonerService;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yan
 * @date: 2019/12/12
 * @description:
 **/

@RestController
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query(@RequestParam("query") String query) {
        return queryService.Query(query);
    }
}
