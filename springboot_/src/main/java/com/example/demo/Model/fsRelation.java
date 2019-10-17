package com.example.demo.Model;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "father")
public class fsRelation {

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
