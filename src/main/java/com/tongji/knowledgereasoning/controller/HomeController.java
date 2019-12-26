package com.tongji.knowledgereasoning.controller;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.github.jsonldjava.utils.Obj;
import com.tongji.knowledgereasoning.service.OntologyReasonerService;
import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import org.apache.jena.rdf.model.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/25
 **/
@Controller
public class HomeController {
    @Autowired
    private OntologyReasonerService ontologyReasonerService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @PostMapping("/ontology-reasoning")
    @ResponseBody
    public Map<String, Object> exchangeData(@RequestBody Map<String,Object> map) throws FileNotFoundException {

        Vector<String> result_vec = ontologyReasonerService.OntologyReasoning();

        Map<String, Object> result_map = new HashMap<>();
        result_map.put("data", result_vec);

//        System.out.println(jsonObject);

        return result_map;
    }


//    @PostMapping("/result")
//    @ResponseBody
//    public Map<String, Object> exchangeData(@RequestBody Map<String,Object> map){
//        System.out.println(map.get("username"));
//
//        Map<String, Object> result_map = new HashMap<>();
//        result_map.put("data", "233423");
//
//        return result_map;
//    }

}
