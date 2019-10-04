package com.example.demo.Controller;

import com.example.demo.Entity.PersonNode;
import com.example.demo.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(path = "/getPersonNodeList", method = RequestMethod.GET)
    public Iterable<PersonNode> getPersonNodeList() {
        return personService.getPersonNodeList();
    }

    @RequestMapping(path="/addPersonNode",method= RequestMethod.GET)
    public PersonNode addPersonNode(@Param("name")String name,@Param("age")int age){
        PersonNode personNode=new PersonNode();
        personNode.setName(name);
        personNode.setAge(age);
        return personService.addPersonNode(personNode);
    }

    @RequestMapping(path="/deletePersonNode",method = RequestMethod.GET)
    public String deletePersonNode(@Param("name")String name){
        personService.deletePersonNode(name);
        return "Successfully delete";
    }
    @RequestMapping(path="/setPersonAge",method = RequestMethod.GET)
    public PersonNode setPersonAge(@Param("name")String name,@Param("age")int age){
        return personService.setPersonAge(name,age);
    }
}
