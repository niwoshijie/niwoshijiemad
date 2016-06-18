package mainpagers.cpackage.t23DesignMode.prototype;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class Client {

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

    /*对象克隆*/



}
