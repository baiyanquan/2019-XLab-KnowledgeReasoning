package com.tongji.knowledgereasoning.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yan
 * @Date: 2019/12/25
 * @Description:
 **/

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String test(){
        return "index";
    }

}
