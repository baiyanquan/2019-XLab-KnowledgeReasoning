package com.doublez.jenademo;


import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import static org.apache.jena.sparql.vocabulary.FOAF.familyName;
import static org.apache.jena.sparql.vocabulary.FOAF.givenName;

/**
 * @program: jenademo
 * @description:
 * @author: doubleZ
 * @create: 2019/10/17
 **/
public class jenaTest {
    // URI 定义
    static String personURI    = "http://somewhere/JohnSmith";
    static String fullName     = "John Smith";
    static String familyName = "Smith";
    static String givenName = "John";

    public static Model model;

    /* 创建model */
    public static void CreateModel(){
        // 创建一个空模型（KG)
        model = ModelFactory.createDefaultModel();

        // 创建一个resource（一个subject)
        Resource johnSmith = model.createResource(personURI);

        //链式API,为resource添加多个Property
        johnSmith.addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N, model.createResource()
                        .addProperty(VCARD.Given, givenName)
                        .addProperty(VCARD.Family, familyName));

        // 添加属性，这里的value是一个literals（文本）
        johnSmith.addProperty(VCARD.FN, fullName);
    }

    /* 遍历model */
    public static void TraverseModel(){
        System.out.println("=================================");

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

        System.out.println("=================================");
    }

    /* 保存为RFD文件 */
    public static void Write_XML_version(){
        model.write(System.out);
    }
    public static void Write_ttl_version(){
        model.write(System.out, "TURTLE");
    }
    public static void Write_ttl_versoin_with_prefix(){
        model.setNsPrefix( "vCard", "http://www.w3.org/2001/vcard-rdf/3.0#" );
        model.setNsPrefix( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#" );
        try {
//            model.write(System.out, "TURTLE");
            model.write(new FileOutputStream("1.rdf"),"TURTLE");      //write to a file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 读取RDF文件 */
    public static void Read_from_RDF(){
        // create an empty model
        model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open( "1.rdf" );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + "1.rdf"  + " not found");
        }

        // read the RDF/XML file
        model.read(in, "","TURTLE");

        // write it to standard out
//        model.write(System.out);

    }

    /* 解析RDF */
    public static void Analysis_RDF(){
        // retrieve the Adam Smith vcard resource from the model
        Resource vcard = model.getResource(personURI);

        // retrieve the value of the N property
        Resource name = (Resource) vcard.getRequiredProperty(VCARD.N)
                .getObject();
        // retrieve the given name property
        String fullName = vcard.getRequiredProperty(VCARD.FN)
                .getString();
        // add two nick name properties to vcard
        vcard.addProperty(VCARD.NICKNAME, "Smithy")
                .addProperty(VCARD.NICKNAME, "Adman");

        // set up the output
        System.out.println("The nicknames of \"" + fullName + "\" are:");
        // list the nicknames
        StmtIterator iter = vcard.listProperties(VCARD.NICKNAME);
        while (iter.hasNext()) {
            System.out.println("    " + iter.nextStatement().getObject()
                    .toString());
        }

        try {
            model.write(System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 查询属性 */
    public static void Query_Property(){
        ResIterator iter = model.listResourcesWithProperty(VCARD.FN);
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextResource()
                        .getRequiredProperty(VCARD.FN)
                        .getString() );
            }
        } else {
            System.out.println("No vcards were found in the database");
        }
    }

    /* 查询Statement */
    public static void Query_Statement(){
        // select all the resources with a VCARD.FN property
        // whose value ends with "Smith"
        StmtIterator iter = model.listStatements(
                new
                        SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                            @Override
                            public boolean selects(Statement s) {
                                return s.getString().endsWith("Smith");
                            }
                        });
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextStatement()
                        .getString());
            }
        } else {
            System.out.println("No Smith's were found in the database");
        }
    }

    public static void main(String[] args){
//        CreateModel();
////        TraverseModel();
////        Write_ttl_versoin_with_prefix();

//        Read_from_RDF();
//        Analysis_RDF();
//        Query_Property();
//        Query_Statement();
    }
}
