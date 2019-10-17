package com.example.demo.Service;

import com.example.demo.Model.PersonNode;

public interface PersonService {

    PersonNode addPersonNode(PersonNode personNode);
    void deletePersonNode(String name);
    Iterable<PersonNode> getPersonNodeList();
    PersonNode setPersonAge(String name,int age);
}