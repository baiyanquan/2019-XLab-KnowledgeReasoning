package rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class ModelAddDelete {
    // some definitions
    static String personURI    = "http://somewhere/xyYan";
    static String givenName = "xy";
    static String familyName = "Yan";
    static String fullName  =givenName+" "+familyName;

    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();

        // create the resource,add the property
        Resource xyYan = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

        System.out.println("原始内容：");
        model.write(System.out);

        // 删除 Statement
        model.remove(model.listStatements(null, VCARD.N, (RDFNode)null));
        model.removeAll(null, VCARD.Given, (RDFNode)null);
        model.removeAll(null, VCARD.Family, (RDFNode)null);

        System.out.println("\n删除后的内容：");
        model.write(System.out);

        //增加 Statement
        model.add(xyYan, VCARD.N, model.createResource()
                .addProperty(VCARD.Given, givenName)
                .addProperty(VCARD.Family, familyName));
        System.out.println("\n重新增加后的内容：");
        model.write(System.out);



    }
}
