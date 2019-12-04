package com.tongji.knowledgereasoning.service;

import com.tongji.knowledgereasoning.dao.LabDao;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service("DataModifyService")
public class ModifyPredicateService {

    @Autowired
    private LabDao labDao;

    public void modifyPredicate() {

        Model model = ModelFactory.createDefaultModel();

        ResultSet rs = labDao.getTriples();

        String namespace = "http://namespace/10.60.38.181";
        String pods = "http://pods/10.60.38.181";
        String services = "http://services/10.60.38.181";
        String containers = "http://containers/10.60.38.181";
        String server = "http://server/10.60.38.181";
        String environment = "http://environment/10.60.38.181";

        while (rs.hasNext()) {

            QuerySolution qs1 = rs.next();

            String subject = qs1.get("s").toString();
            String object = qs1.get("o").toString();
            String predicate = qs1.get("p").toString();


            if ((subject.contains(namespace) || subject.contains(pods) || subject.contains(containers) ||
                    subject.contains(services) || subject.contains(server) || subject.contains(environment))
                    && (qs1.get("o").isResource()
                    && (object.contains(namespace) || object.contains(server) || object.contains(pods) ||
                    object.contains(services) || object.contains(containers) || object.contains(environment)))) {

                //System.out.println(subject);
                //System.out.println(predicate);

                String[] p = predicate.split("/");
                String predicate_ = subject.substring(0, subject.indexOf("1")) + "10.60.38.181/" + p[p.length - 1];

                //System.out.println(predicate);
                //System.out.println('\n');

                model.add(model.createResource(subject), model.createProperty(predicate_), model.createResource(object));

            }
        }
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter("data/labdata.ttl");//没有文件会自动创建
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.write(fwriter,"TURTLE");	//写入文件,默认是xml方式,可以自己指定

    }

}
