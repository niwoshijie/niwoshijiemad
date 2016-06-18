package mainpagers.cpackage.t23DesignMode.factory;

/**
 * Created by LiuShao on 2016/6/13.
 */
public abstract class Factory {
    //抽象工厂类，具体生产什么由子类去实现
//    public abstract Product createProduct();
    public abstract <T extends Product> T createProduct (Class<T> clz);

}
