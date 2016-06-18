package mainpagers.cpackage.t23DesignMode.builder;

/**
 * Created by LiuShao on 2016/5/19.
 * 抽象的Builder类，规范产品的组组建，一般是由子类实现具体的组件过程
 */
public abstract class Builder {
    //设置主机
    public abstract void BuildBoard(String board);

    public abstract void BuildDisplay(String display);

    public abstract void BuildOS();

    public abstract Computer create();

}
