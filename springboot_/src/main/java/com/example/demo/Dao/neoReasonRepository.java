package com.example.demo.Dao;

import com.example.demo.Model.PersonRelation;
import com.example.demo.Model.fsRelation;
import com.example.demo.Model.msRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface neoReasonRepository extends Neo4jRepository<PersonRelation,Long> {

    @Query("MATCH p=(p1:Person)-[r:father]->(p2:Person) RETURN p")
    List<fsRelation> fAndS();

    @Query("MATCH p=(p1:Person)-[r:mother]->(p2:Person) RETURN p")
    List<msRelation> mAndS();
}
