package com.example.demo.Dao;

import com.example.demo.Model.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode,Long>{

    @Query("MATCH(p:Person{name:{name}}) DELETE p")
    PersonNode deletePersonNodeByName(@Param("name") String name);

    @Query("MATCH(p:Person{name:{name}}) SET p.age={age} RETURN p")
    PersonNode setPersonAge(@Param("name") String name,@Param("age")int age);

}
