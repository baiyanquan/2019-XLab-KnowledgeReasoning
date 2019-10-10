package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveMovieTest {

    @Autowired
    MovieRepository movieRepo;

    @Test
    public void testSaveMovie() {

        movieRepo.deleteAll();
        Movie m1 = new Movie("无问西东", "2018");
        Movie m2 = new Movie("罗曼蒂克消亡史", "2016");
        movieRepo.save(m1);
        movieRepo.save(m2);

    }
}
