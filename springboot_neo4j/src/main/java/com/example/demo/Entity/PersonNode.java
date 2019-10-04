package com.example.demo.Entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import javax.validation.GroupSequence;

@NodeEntity(label = "Person")
public class PersonNode {

    @GraphId
    private Long nodeId;

    @Property(name = "name")
    private String name;

    @Property(name="age")
    private int age;

    public long getNodeId(){
        return nodeId;
    }
    public void setNodeId(long Id){this.nodeId=Id;}
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }

}