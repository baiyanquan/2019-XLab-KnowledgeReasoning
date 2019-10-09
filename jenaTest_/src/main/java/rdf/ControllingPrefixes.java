package rdf;

import org.apache.jena.rdf.model.*;

public class ControllingPrefixes{

   public static void main(String []args){

       Model m = ModelFactory.createDefaultModel();
       String nsA = "http://somewhere/else#";
       String nsB = "http://nowhere/else#";
       Resource root = m.createResource( nsA + "root" );
       Resource x = m.createResource( nsA + "x" );
       Resource y = m.createResource( nsA + "y" );
       Resource z = m.createResource( nsA + "z" );

       Property P = m.createProperty( nsA + "P" );
       Property Q = m.createProperty( nsB + "Q" );

       //层叠增加三个Statement
       //add 的三个参数分别是三元组的主语、谓语和客体
       m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
       System.out.println( "# -- no special prefixes defined" );
       m.write( System.out );

       //setNsPrefix 函数用于设置名字空间前缀
       System.out.println( "# -- nsA defined" );
       m.setNsPrefix( "nsA", nsA );
       m.write( System.out );

       System.out.println( "# -- nsA and cat defined" );
       m.setNsPrefix( "nsB", nsB );
       m.write( System.out );
   }

}
