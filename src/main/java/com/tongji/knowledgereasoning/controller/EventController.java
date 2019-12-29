package com.tongji.knowledgereasoning.controller;

import com.tongji.knowledgereasoning.service.EventService;
import com.tongji.knowledgereasoning.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: yan
 * @Date: 2019/12/28
 * @Description:
 **/
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/eventQuery", method = RequestMethod.POST)
    public List<String> query(@RequestParam("service")String service) {
        return eventService.eventQuery(service);
    }
}
