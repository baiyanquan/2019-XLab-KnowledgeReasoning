package TDB;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

/*
建立Dataset：首先，需要建立一个Dataset对象。Dataset是TDB的一个封装类，可简单地视其为一个数据库；
装载Model：其次，需要装载一个Model。Jena的操作都是围绕Model数据结构展开，可视每一个Model为数据库中的一张表。一个Dataset对象里可包含多个Model，类似于一个数据库里面可以包含多张表，这些Model都有各自的名字，用户可以访问某个Model，而不用把所有Model加载到内存。Dataset中默认存在一个DefaultModel；
固化TDB文件：再次，装载Model时，需要输入TDB文件夹的地址。该位置就是将包含了RDF数据的Model，固化到TDB的地址。在下次启动时，可以从这个TDB文件地址，读取到之前固化的各个Model。
提交和关闭操作：最后，所有update完成后，需要提交Model，提交Dataset，进行事务提交。然后一定要关闭Model，关闭Dataset。
 */

public class tdbTest {

    public static void main(String []args) {

        // Direct way: Make a TDB-back Jena model in the named directory.
        /*String directory = "tdbtest" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model model = ds.getDefaultModel() ;
        FileManager.get().readModel(model, "...");//读取RDF文件到指定的model里面

        model.commit();//这里类似于数据库的commint，意思是把现在的操作立刻提交

        // ... do work ...

        model.close();//结束使用的时候，一定要对Model和Dataset进行关闭
        ds.close();
*/
    }

}
