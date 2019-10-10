package com.doublez.neo4jdemo.repository;




import com.doublez.neo4jdemo.Neo4jdemoApplicationTests;
import com.doublez.neo4jdemo.domain.CalledRelationship;
import com.doublez.neo4jdemo.domain.ServiceNode;
import com.doublez.neo4jdemo.domain.UserNode;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;
@Component
public class CalledRelationshipRepositoryTest extends Neo4jdemoApplicationTests {
    Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    CalledRelationshipRepository calledRelationshipRepository;

    @Test
    public void test(){
        UserNode userNode = new UserNode();
        userNode.setName("lucky");
        userNode.setUserId("123");
        ServiceNode serviceNode = new ServiceNode();
        serviceNode.setId(new Long(28));
//        serviceNode.setName("保险服务123");
        CalledRelationship calledRelationship = new CalledRelationship();
        calledRelationship.setCalledNum(123);
        calledRelationship.setUserNode(userNode);
        calledRelationship.setServiceNode(serviceNode);
        CalledRelationship save = calledRelationshipRepository.save(calledRelationship);
        System.out.println(save);
    }
}