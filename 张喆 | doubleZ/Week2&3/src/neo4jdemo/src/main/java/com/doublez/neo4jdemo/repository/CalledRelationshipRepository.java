package com.doublez.neo4jdemo.repository;

import com.doublez.neo4jdemo.domain.CalledRelationship;
import com.doublez.neo4jdemo.domain.ServiceNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalledRelationshipRepository extends Neo4jRepository<CalledRelationship,Long>{

}
