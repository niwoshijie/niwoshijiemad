package mainpagers.cpackage.t23DesignMode.observeDesign;

/**
 * Created by Administrator on 2016/5/25.
 */
public class Test {

    public void test(){
        //被观察者
        DevTechFrontier devTechFrontier = new DevTechFrontier();
        //观察者
        Coder mrsimple = new Coder("LIU");
        Coder mrSimple1 = new Coder("SHAO");
        Coder mrSimple2 = new Coder("BO");
        //将观察者注册到可观察对象的观察者列表中
        devTechFrontier.addObserver(mrsimple);
        devTechFrontier.addObserver(mrSimple1);
        devTechFrontier.addObserver(mrSimple2);

        //发布消息
        devTechFrontier.postNewPublication("有了新的技术支持了");





    }


}
