package com.example.demo.Model;

public class TripleModel {

    private String subject;
    private String predicate;
    private String object;

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject(){
        return subject;
    }

    public void setPredicate(String predicate){
        this.predicate=predicate;
    }
    public String getPredicate(){
        return predicate;
    }

    public void setObject(String object){
        this.object=object;
    }
    public String getObject(){
        return object;
    }

}
