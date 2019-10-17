package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Person findByName(String name);
}