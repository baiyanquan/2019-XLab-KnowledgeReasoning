package com.tongji.knowledgereasoning.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${decisionEngine.url}",name="transe")
public interface TranseService {
    @RequestMapping(value = "/TransE_Reasoning", method = RequestMethod.GET)
    public void TransEReasoning();
}