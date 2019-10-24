package com.example.demo.Dao;

import com.example.demo.Model.PersonRelation;
import com.example.demo.Model.TripleModel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRelationRepository extends Neo4jRepository<PersonRelation,Long> {

    @Query("MATCH (p1:Person),(p2:Person) WHERE p1.name={name1} AND p2.name={name2} " +
            "CREATE p=(p1)-[r:relatives]->(p2) RETURN p")
    PersonRelation addPersonRelation(@Param("name1")String name1, @Param("name2")String name2);

    @Query("MATCH p=(p1:Person)<-[r:relatives]->(p2:Person) " +
            "WHERE p1.name={name1} AND p2.name={name2} DELETE r")
    List<PersonRelation>deletePersonRelation(@Param("name1")String name1, @Param("name2")String name2);

}
