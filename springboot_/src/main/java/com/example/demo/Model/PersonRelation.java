package com.example.demo.Model;

import org.neo4j.ogm.annotation.*;
@RelationshipEntity(type = "relatives")
public class PersonRelation {
    @GraphId
    private long Id;

    @StartNode
    private PersonNode startNode;
    @EndNode
    private PersonNode endNode;

    public PersonNode getStartNode(){
        return startNode;
    }
    public void setStartNode(PersonNode startNode){
        this.startNode=startNode;
    }

    public PersonNode getEndNode(){
        return endNode;
    }
    public void setEndNode(PersonNode endNode){
        this.endNode=endNode;
    }

}
