package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoveMovieTest {

    @Autowired
    MovieRepository movieRepo;

    @Test
    public void testRemoveMovie(){
        Movie m = movieRepo.findByTitle("无问西东");
        movieRepo.delete(m);
    }
}
