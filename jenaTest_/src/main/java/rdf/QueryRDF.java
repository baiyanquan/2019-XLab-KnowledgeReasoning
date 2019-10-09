package rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;
import java.io.InputStream;

/*
Model.listStatements(): 列出Model 所有的Statements。
Model.listSubjects(): 列出所有具有属性的资源。
Model.listSubjectsWithProperty(Property p, RDFNode o): 列出所有具有属性p 且其值为 o 的资源。
上面所述的几种查询都是对 Model.listStatements(Selector s) 进行了一些包装得到的。如
Selector selector = new SimpleSelector(subject, predicate, object).
这个选择器选择所有主语符合 subject、谓语符合 predicate、客体符合 object 的Statement。

 */
public class QueryRDF {

    /*Reading RDF*/
    static final String inputFileName = "src/data/vc-db-1.rdf";

    public static void main(String[] args) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        // read the RDF/XML file,第二个参数是URI，它将用于解析相对URI
        model.read(in, "");

        /*
        查询具有 fullName 的资源。
         */

        //使用 listResourcesWithProperty
        ResIterator iter = model.listResourcesWithProperty(VCARD.FN);
        if (iter.hasNext()) {
            System.out.println("The database contains vcard for:");
            while (iter.hasNext()) {
                System.out.println(iter.nextResource().getProperty(VCARD.FN).getString());
            }
        } else {
            System.out.println("No vcards were found in the database");
        }

        //使用 Selector
        StmtIterator iter_ = model.listStatements(new SimpleSelector(null, VCARD.FN, (RDFNode) null));
        if (iter_.hasNext()) {
            System.out.println("The database contains vcard for:");
            while (iter_.hasNext()) {
                System.out.println(iter_.nextStatement().getString());
            }
        } else {
            System.out.println("No vcards were found in the database");
        }

        // select all the resources with a VCARD.FN property
        // whose value ends with "Smith"
        StmtIterator iter0 = model.listStatements(
                new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                    public boolean selects(Statement s) {
                        return s.getString().endsWith("Smith");
                    }
                });

        if (iter0.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter0.hasNext()) {
                System.out.println(iter0.nextStatement()
                        .getString());
            }
        } else {
            System.out.println("No Smith's were found in the database");
        }
    }
}




