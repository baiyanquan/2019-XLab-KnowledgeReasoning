package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.NeoDao;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service("QueryService")
public class QueryService {

    @Autowired
    private NeoDao neoDao;

    public String Query(String query) {

        Model model = ModelFactory.createDefaultModel();
        java.sql.ResultSet rs = neoDao.getTriples();

        try {
            while (rs.next()) {
                String subject = rs.getString(1);
                String predicate = rs.getString(2);
                String object = rs.getString(3);

                String[] p = predicate.split("__");
                predicate = p[p.length - 1];
                // System.out.println(predicate);
                //System.out.println(predicate);
                model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Query q = QueryFactory.create(query);  //创建一个查询
        // 执行查询，获得结果
        QueryExecution qe = QueryExecutionFactory.create(q, model);
        org.apache.jena.query.ResultSet resultSet = qe.execSelect();//select 类型

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, resultSet);
        String result = new String(outputStream.toByteArray());
        return result;
    }

}
