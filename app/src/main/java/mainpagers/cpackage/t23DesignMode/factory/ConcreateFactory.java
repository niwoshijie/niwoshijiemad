package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 */
//具体的工厂类，通过反射获取类的示例代码
public class ConcreateFactory extends Factory{

//    @Override
//    public Product createProduct() {
//        return new ConcreteProductA();
//    }

    @Override
    public <T extends Product> T createProduct(Class<T> clz) {
       Product p = null;
        try {
            p = (Product) Class.forName(clz.getName()).newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) p;
    }

}
