package com.example.demo.Controller;

import com.example.demo.Entity.PersonRelation;
import com.example.demo.Service.PersonRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PersonRelationController {
    @Autowired
    private PersonRelationService personRelationService;

    @RequestMapping(path="/addPersonRelation",method = RequestMethod.GET)
    public PersonRelation addPersonRelation(String name1,String name2){
        return personRelationService.addPersonRelation(name1,name2);
    }

    @RequestMapping(path="/deletePersonRelation",method = RequestMethod.GET)
    public String deletePersonRelation(String name1,String name2){
        personRelationService.deletePersonRelation(name1,name2);
        return "Successfully delete";
    }

}
