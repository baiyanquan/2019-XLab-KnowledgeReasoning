package com.example.demo.Service;

import com.example.demo.Dao.PersonRelationRepository;
import com.example.demo.Model.PersonRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("PersonRelationService")
public class PersonRelationServiceImp implements PersonRelationService{

    @Autowired
    private PersonRelationRepository personRelationRepository;

    public PersonRelation addPersonRelation(String name1,String name2){
        return personRelationRepository.addPersonRelation(name1,name2);
    }

    public void deletePersonRelation(String name1,String name2){
        personRelationRepository.deletePersonRelation(name1,name2);
    }
}
