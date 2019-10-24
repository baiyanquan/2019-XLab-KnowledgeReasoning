package com.example.demo.Service;

import com.example.demo.Dao.PersonRepository;
import com.example.demo.Dao.neoReasonRepository;
import com.example.demo.Model.PersonNode;
import com.example.demo.Model.fsRelation;
import com.example.demo.Model.msRelation;
import org.apache.jena.rdf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service("neoTOfuseki")
public class neoTOfusekiServiceImp {
    @Autowired
    private neoReasonRepository nRR;

    @Autowired
    private PersonRepository personRepository;

    public void test() {

        List<fsRelation> fAndS = nRR.fAndS();
        List<msRelation> mAndS = nRR.mAndS();
        Model model = ModelFactory.createDefaultModel();

        String URI = "http://kg/Person#";
        Property name = model.createProperty(URI + "name");
        Property age = model.createProperty(URI + "age");
        personRepository.findAll();

        for (PersonNode it : personRepository.findAll()) {

            String uri = URI + String.valueOf(it.getNodeId());

            //create the resource,add the property
           Resource resource = model.createResource(uri)
                    .addProperty(name, it.getName())
                    .addProperty(age, String.valueOf(it.getAge()));
           // model.add(model.createResource(uri), model.createProperty(URI + "name"), model.createResource(it.getName()));
            //model.add(model.createResource(uri), model.createProperty(URI + "age"), model.createResource(String.valueOf(it.getAge())));

        }

        for(fsRelation it:fAndS) {
            String subject=String.valueOf(it.getStartNode().getNodeId());
            String object=String.valueOf(it.getEndNode().getNodeId());
            model.add(model.createResource(URI+subject), model.createProperty(URI+"father"), model.createResource(URI+object));
        }

        for(msRelation it:mAndS) {
            String subject=String.valueOf(it.getStartNode().getNodeId());
            String object=String.valueOf(it.getEndNode().getNodeId());
            model.add(model.createResource(URI+subject), model.createProperty(URI+"mother"), model.createResource(URI+object));
        }

        model.write(System.out, "TURTLE");

        FileWriter fwriter = null;
        try {
           // fwriter = new FileWriter("F:\\test0.xml");
           fwriter = new FileWriter("F:\\test0.ttl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //model.write(fwriter);
        model.write(fwriter,"TURTLE");

    }
    /*private static void updateTriplesInFuseki(Model model) {

        StmtIterator itr = model.listStatements();
        RDFConnectionRemoteBuilder myBuilder = RDFConnectionFuseki.create().destination("http://localhost:3030//test/update");

        try (RDFConnection conn = (RDFConnectionFuseki)myBuilder.build() ){

            while (itr.hasNext()) {

                Statement nowStatement = itr.nextStatement();
                String subject = nowStatement.getSubject().toString();
                String predicate = nowStatement.getPredicate().toString();
                String object = nowStatement.getObject().toString();
                String insert = "delete{<" + subject + "> <"+ predicate +"> <" + object + ">} where {}";

                conn.update(insert);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
