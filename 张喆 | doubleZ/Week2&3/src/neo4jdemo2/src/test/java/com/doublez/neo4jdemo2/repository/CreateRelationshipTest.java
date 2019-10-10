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
public class CreateRelationshipTest {

    @Autowired
    MovieRepository movieRepo;
    @Autowired
    PersonRepository personRepo;

    @Test
    public void testCreateRelationship(){
        Person p1 = personRepo.findByName("章子怡");
        Person p2 = personRepo.findByName("李芳芳");
        Person p3 = personRepo.findByName("程耳");

        Movie m1 = movieRepo.findByTitle("罗曼蒂克消亡史");
        Movie m2 = movieRepo.findByTitle("无问西东");

        if (m1!=null) {
            if(p1!=null){
                p1.addActor(m1);
            }
            if(p3!=null){
                p3.addDirector(m1);
            }
        }
        if (m2!=null) {
            if(p1!=null){
                p1.addActor(m2);
            }
            if(p2!=null){
                p2.addDirector(m2);
            }
        }

        personRepo.save(p1);
        personRepo.save(p2);
        personRepo.save(p3);
    }
}
