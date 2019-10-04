package com.example.demo.Service;

import com.example.demo.Entity.PersonRelation;

public interface PersonRelationService {

    PersonRelation addPersonRelation(String name1, String name2);
    void deletePersonRelation(String name1,String name2);
}
