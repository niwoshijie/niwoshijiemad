package liushaobo.mad;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mainpagers.cpackage.t23DesignMode.abstractfactory.CarFactory;
import mainpagers.cpackage.t23DesignMode.abstractfactory.QQFactory;
import mainpagers.cpackage.t23DesignMode.abstractfactory.SUVFactory;
import mainpagers.cpackage.t23DesignMode.prototype.WordDocument;

import static org.junit.Assert.assertEquals;


public class ExampleUnitTest {

    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public void test(){
        //构建文档对象
        WordDocument originDoc = new WordDocument();
        //编辑文档，添加图片
        originDoc.addImage("abc");
        originDoc.addImage("def");
        originDoc.addImage("ghi");
        originDoc.showDocument();
        //以原始文档为原型，拷贝一份副本
        WordDocument document = null;
        try {
            document = (WordDocument) originDoc.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        document.showDocument();
        //修改文档副本不会影响原始文档
        document.setmText("修改过的文档");
        document.showDocument();
        originDoc.showDocument();
    }



    public void testAA(){
        CarFactory factory = new QQFactory();
        factory.createBrake().brake();
        factory.createEngine().engine();
        factory.createItire().tire();

        CarFactory factory1 = new SUVFactory();
        factory1.createEngine().engine();
    }

    @Test
    public void TestRound(){
        long a = Math.round(11.5);
        System.out.print("a"+a);
        long b = Math.round(-11.5);
        System.out.print("b"+b);

        Set<String> set = new HashSet<String>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        set.add("d");
        set.add("d");
        set.add("d");
        set.add("uuu");
        set.add("e");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }





}