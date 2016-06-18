package liushaobo.mad;

import org.junit.Test;

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



    @Test
    public void testAA(){
        CarFactory factory = new QQFactory();
        factory.createBrake().brake();
        factory.createEngine().engine();
        factory.createItire().tire();

        CarFactory factory1 = new SUVFactory();
        factory1.createEngine().engine();

    }


}