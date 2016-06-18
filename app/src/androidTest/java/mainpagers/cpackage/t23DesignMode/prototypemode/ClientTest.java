package mainpagers.cpackage.t23DesignMode.prototypemode;

import junit.framework.TestCase;

import mainpagers.cpackage.t23DesignMode.prototype.WordDocument;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class ClientTest extends TestCase {

    public void testTest1() throws Exception {
        //构建文档对象
        WordDocument originDoc = new WordDocument();
        //编辑文档，添加图片
        originDoc.addImage("abc");
        originDoc.addImage("def");
        originDoc.addImage("ghi");
        originDoc.showDocument();
        //以原始文档为原型，拷贝一份副本
        WordDocument document = (WordDocument) originDoc.clone();
        document.showDocument();
        //修改文档副本不会影响原始文档
        document.setmText("修改过的文档");
        document.showDocument();
        originDoc.showDocument();
    }
}