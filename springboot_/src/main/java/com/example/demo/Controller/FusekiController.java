package com.example.demo.Controller;

import com.example.demo.Model.TripleModel;
import com.example.demo.Service.fusekiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FusekiController {

    @Autowired
    private fusekiService fS;

    @RequestMapping(path="/fusekiQuery")
     public List<TripleModel> t(){
       return fS.queryList();
     }

}
