package com.example.demo.Controller;

import com.example.demo.Service.neoTOfusekiServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class neoTofusekiController {

    @Autowired
    private neoTOfusekiServiceImp neoTOfusekiServiceImp;

    @RequestMapping(path="/test",method= RequestMethod.GET)
    public String test(){
        neoTOfusekiServiceImp.test();
        return "test";
    }
}
