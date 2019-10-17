package com.doublez.neo4jdemo2.repository;

import com.doublez.neo4jdemo2.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemovePersonTest {

    @Autowired
    PersonRepository personRepo;

    @Test
    public void testRemoveMovie(){
        Person p = personRepo.findByName("章子怡");
        personRepo.delete(p);
    }
}
