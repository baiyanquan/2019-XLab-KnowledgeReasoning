package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(String title);
}