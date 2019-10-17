package Reasoner;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class RdfOwlReasoner {

    public static void main(String []args) {
        String fiance = "http://www.demo/fiance#";

        Model m = ModelFactory.createDefaultModel();
        Resource shb = m.createResource(fiance + "孙宏斌");
        Resource rczg = m.createResource(fiance + "融创中国");
        Property control = m.createProperty(fiance + "执掌");
        m.add(shb, control, rczg);

        Resource jyt = m.createResource(fiance + "贾跃亭");
        Resource lsw = m.createResource(fiance + "乐视网");
        m.add(jyt, control, lsw);

        Resource dcgs = m.createResource(fiance+"地产公司");
        m.add(rczg,RDF.type,dcgs);

        Resource gs = m.createResource(fiance+"公司");
        m.add(dcgs, RDFS.subClassOf,gs);

        Resource frst = m.createResource(fiance+"法人实体");
        m.add(gs, RDFS.subClassOf,frst);

        m.add(shb,RDF.type,gs);

        Resource r = m.createResource(fiance+"人");
        m.add(shb,RDF.type,r);

        m.add(r, OWL2.disjointWith,gs);

        /*
        RDFS Reasoner
         */
        InfModel inf_rdfs = ModelFactory.createRDFSModel(m);
        subClassOf(inf_rdfs,m.getResource(fiance+"地产公司"),m.getResource(fiance+"法人实体"));

        /*
        OWL Reasoner
        */
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel Inf_owl = ModelFactory.createInfModel(reasoner,m);
        printStatements(Inf_owl,rczg,RDF.type,null);

    }

    public static void subClassOf(Model m, Resource s, Resource o){
        for(StmtIterator i = m.listStatements(s,RDFS.subClassOf,o);i.hasNext();){
            Statement stmt = i.nextStatement();
            System.out.println("-"+ PrintUtil.print(stmt));
            System.out.println("yes!");
            break;
        }
    }

    public static void printStatements(Model m,Resource s, Property p,Resource o){

        for(StmtIterator i = m.listStatements(s,p,o);i.hasNext();){
            Statement stmt = i.nextStatement();
            System.out.println("-"+ PrintUtil.print(stmt));
        }
    }

}
