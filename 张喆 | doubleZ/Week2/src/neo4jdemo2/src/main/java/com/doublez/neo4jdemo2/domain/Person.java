package com.doublez.neo4jdemo2.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String born;

    public Person() {// 从 Neo4j API 2.0.5开始需要无参构造函数

    }

    public Person(String name, String born) {
        this.name = name;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBorn() {
        return born;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    @Relationship(type = "ACTED_IN", direction = Relationship.OUTGOING)
    public Set<Movie> actors;

    public void addActor(Movie movie) {
        if (actors == null) {       //如果没作为过任何电影的演员，则创建哈希表
            actors = new HashSet<>();
        }
        actors.add(movie);
    }

    public void removeActor(Movie movie){
        if(actors == null){
            return;
        }

        boolean flag = false;
        Iterator<Movie> i = actors.iterator();
        while(i.hasNext()){
            if(i.next().getTitle().equals(movie.getTitle())){
                i.remove();
                flag = true;
                break;
            }
        }

        if(!flag){
            System.out.println("「" + this.name + "」不是电影《" + movie.getTitle() + "》的演员");
        }
    }

    @Relationship(type = "DIRECTED", direction = Relationship.OUTGOING)
    public Set<Movie> directors;

    public void addDirector(Movie movie) {
        if (directors == null) {
            directors = new HashSet<>();
        }
        directors.add(movie);
    }
}