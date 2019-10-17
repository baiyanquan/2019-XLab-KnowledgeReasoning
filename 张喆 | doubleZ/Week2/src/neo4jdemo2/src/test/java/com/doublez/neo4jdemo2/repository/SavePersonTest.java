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
public class SavePersonTest {

    @Autowired
    PersonRepository personRepo;

    @Test
    public void testSavePerson() {
        personRepo.deleteAll();

        Person p1 = new Person("章子怡", "1979");
        Person p2 = new Person("李芳芳", "1976");
        Person p3 = new Person("程耳", "1970");

        personRepo.save(p1);
        personRepo.save(p2);
        personRepo.save(p3);
    }
}
