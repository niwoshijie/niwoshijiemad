package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 */
public class Client {

    public void test(){
        Factory factory = new ConcreateFactory();
        Product p = factory.createProduct(ConcreteProductB.class);
        p.method();
    }


}
