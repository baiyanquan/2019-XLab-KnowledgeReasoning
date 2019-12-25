package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.NeoDao;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


@Service("QueryService")
public class QueryService {

    @Autowired
    private NeoDao neoDao;
    private List<String> queryResult = new ArrayList<>();

    public List<String> Query(String query) {

        Model model = ModelFactory.createDefaultModel();
        java.sql.ResultSet rs = neoDao.getTriples();

        try {

            while (rs.next()) {
                String subject = rs.getString(1);
                String predicate = rs.getString(2);
                String object = rs.getString(3);

                if(predicate.equals("ns1__contains")){
                    predicate = "http://pods/10.60.38.181/constains";
                }else if(predicate.equals("ns1__deployed_in")){
                    predicate = "http://pods/10.60.38.181/deployed_in";
                }else if(predicate.equals("ns1__provides")){
                    predicate = "http://pods/10.60.38.181/provides";
                }else if(predicate.equals("ns2__profile")){
                    predicate = "http://services/10.60.38.181/provides";
                }else if(predicate.equals("ns3__profile")){
                    predicate = "http://containers/10.60.38.181/profile";
                }else if(predicate.equals("ns4__has")){
                    predicate = "http://environment/10.60.38.181/has";
                }else if(predicate.equals("ns5__manage")){
                    predicate = "http://servers/10.60.38.181/manage";
                }else if(predicate.equals("ns6__supervises")){
                    predicate = "http://namespace/10.60.38.181/supervises";
                }else if(predicate.equals("rdfs__domain")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#domain";
                }else if(predicate.equals("rdfs__range")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#range";
                }else if(predicate.equals("rdfs__subClassOf")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#subClassOf";
                }else if(predicate.equals("rdfs__subPropertyOf")){
                    predicate = "http://www.w3.org/2000/01/rdf-schema#subPropertyOf";
                }

                model.add(model.createResource(subject), model.createProperty(predicate), model.createResource(object));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 属性路径查询表达式
//        Query propertyPathQuery = QueryFactory.create(
//                "PREFIX pods_rel: " + "<http://pods/10.60.38.181/> \n" +
//                        "SELECT * " +
//                        "{" +
//                        "<http://services/10.60.38.181/sock-shop/orders>" + " ^pods_rel:provides / ( pods_rel:deployed_in* | pods_rel:contains* ) ?o ." +
//                        "}");


//        Query querys = QueryFactory.create(
//                "PREFIX pods_rel: " + "<http://pods/10.60.38.181/> \n" +
//                        "SELECT * " +
//                        "{" +
//                        "?s ?p <http://services/10.60.38.181/sock-shop/orders> ." +
//                        "}");

        Query propertyPathQuery = QueryFactory.create(query);  //创建一个查询
        // 执行查询，获得结果
        QueryExecution qe = QueryExecutionFactory.create(propertyPathQuery, model);
        org.apache.jena.query.ResultSet resultSet = qe.execSelect();//select 类型
        if(query.contains("?o")) {
            while (resultSet.hasNext()) {
                QuerySolution qs = resultSet.next();
                String object = qs.get("o").toString();
                queryResult.add(object);
            }
        }else if(query.contains("?s")){
            while (resultSet.hasNext()) {
                QuerySolution qs = resultSet.next();
                String object = qs.get("s").toString();
                queryResult.add(object);
            }
        }

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ResultSetFormatter.outputAsJSON(outputStream, resultSet);
//        String result = new String(outputStream.toByteArray());

        return queryResult;
    }

}
