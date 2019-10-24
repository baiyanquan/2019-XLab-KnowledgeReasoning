package com.example.demo.Controller;


import com.example.demo.Service.neoTOfusekiServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloworldController {

    @RequestMapping("/")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(path="/hello",method= RequestMethod.GET)
    public String test(@Param("name")String name, @Param("age")int age){

        return name+age;

    }

}
