package rdf;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class ModelAccess {

    // some definitions
    static String personURI    = "http://somewhere/xyYan";
    static String givenName = "xy";
    static String familyName = "Yan";
    static String fullName  =givenName+" "+familyName;

    public static void main(String[] args){

        Model model = ModelFactory.createDefaultModel();

        // create the resource,add the property
        Resource xyYan= model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given,givenName)
                                .addProperty(VCARD.Family,familyName));


        /*
        Model 的 getResource 方法：该方法根据参数返回一个资源对象。
        Resource 的 getProperty 方法：根据参数返回一个属性对象。
        Property 的 getObject 方法：返回属性值。使用时根据实际类型是 Resource 还是 literal 进行强制转换。
        Property 的 getResource 方法：返回属性值的资源。如果属性值不是Resource，则报错。
        Property 的 getString 方法：返回属性值的文本内容。如果属性值不是文本，则报错。
        Resource 的 listProperties 方法：列出所找到符合条件的属性
        */

        // 从 Model 获取资源
        Resource vcard = model.getResource(personURI);
		/*
		// 获取N 属性的值（用属性的 getObject()方法）
		Resource name = (Resource) vcard.getProperty(VCARD.N)
		                                .getObject();
		*/

        // 如果知道属性的值是资源，可以使用属性的getResource 方法
        Resource name = vcard.getProperty(VCARD.N).getResource();

        // 属性的值若是 literal，则使用 getString 方法
        fullName = vcard.getProperty(VCARD.FN).getString();

        //  增加两个 NICKNAME 属性
        vcard.addProperty(VCARD.NICKNAME, "X")
                .addProperty(VCARD.NICKNAME, "Y");

        System.out.println("The nicknames of \""
                + fullName + "\" are:");

        // 列出两个NICKNAME 属性，使用资源的 listProperties 方法
        StmtIterator iter = vcard.listProperties(VCARD.NICKNAME);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement()
                    .getObject()
                    .toString());
        }
    }
}
