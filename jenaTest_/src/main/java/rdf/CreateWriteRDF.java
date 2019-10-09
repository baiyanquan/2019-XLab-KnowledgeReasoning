package rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class CreateWriteRDF extends Object {
    // some definitions
    static String personURI    = "http://somewhere/xyYan";
    static String givenName = "xy";
    static String familyName = "Yan";
    static String fullName  =givenName+" "+familyName;

    public static void main (String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // create the resource,add the property
        Resource xyYan= model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                             model.createResource()
                                     .addProperty(VCARD.Given,givenName)
                                      .addProperty(VCARD.Family,familyName));

        /*Statement*/
        // list the statements in the Model
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");

            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }

        /*Writing RDF*/
        // now write the model in XML form to a file
        model.write(System.out);

        // now write the model in XML form to a file
        model.write(System.out, "RDF/XML-ABBREV");

        // now write the model in N-TRIPLES form to a file
        model.write(System.out, "N-TRIPLES");
    }
}
