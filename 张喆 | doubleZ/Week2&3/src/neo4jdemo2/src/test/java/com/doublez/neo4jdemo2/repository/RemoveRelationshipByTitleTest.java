package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Movie;
import com.doublez.neo4jdemo2.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoveRelationshipByTitleTest {

    @Autowired
    PersonRepository personRepo;
    @Autowired
    MovieRepository movieRepo;

    @Test
    public void testRemoveRelationshipByTitle(){
        Person p = personRepo.findByName("章子怡");
        Movie m = movieRepo.findByTitle("无问西东");

        p.removeActor(m);

        personRepo.save(p);
    }
}
