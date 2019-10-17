package FusekiTest;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

public class fusekiTest {

    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3030//movie/query");
        Query query = QueryFactory.create("SELECT ?s ?p?o\n" +
                                             "WHERE {\n" +
                                             "  ?s ?p ?o\n" +
                                             "}\n" +
                                             "LIMIT 5");

        RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build();
        QueryExecution qExec = conn.query(query);
        ResultSet resultSet = qExec.execSelect();

        while (resultSet.hasNext()) {

                QuerySolution querySolution = resultSet.next();
                String subject = querySolution.get("s").toString();
                String object = querySolution.get("o").toString();
                String predicate = querySolution.get("p").toString();

                model.add(model.createResource(subject),
                        model.createProperty(predicate),
                        model.createResource(object));
        }

        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement());
        }
        qExec.close();
    }
}
