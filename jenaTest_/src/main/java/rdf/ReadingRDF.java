package rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.*;

public class ReadingRDF{

    /*Reading RDF*/
    static final String inputFileName  = "src/data/vc-db-1.rdf";

    public static void main (String args[]) {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file,第二个参数是URI，它将用于解析相对URI
        model.read(in, "");

        // write it to standard out
        model.write(System.out);
    }
}
