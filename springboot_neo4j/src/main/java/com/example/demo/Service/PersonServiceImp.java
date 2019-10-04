package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dao.PersonRepository;
import com.example.demo.Entity.PersonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PersonNodeService")
public class PersonServiceImp implements PersonService{
    @Autowired
    private PersonRepository personRepository;

    public Iterable<PersonNode> getPersonNodeList(){
         return personRepository.findAll();
    }
    public PersonNode addPersonNode(PersonNode personNode){
        return personRepository.save(personNode);
    }

    public void deletePersonNode(String name){
        personRepository.deletePersonNodeByName(name);
    }

    public PersonNode setPersonAge(String name,int age){
        return personRepository.setPersonAge(name,age);
    }

}
