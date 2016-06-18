package mainpagers.cpackage.t23DesignMode.abstractfactory;

/**
 * Created by LiuShao on 2016/6/17.
 * 抽象工厂类的简单实用
 */
public abstract class CarFactory {

    /*生产轮胎*/
    public abstract ITire createItire();

    /*生产发动机*/
    public abstract  IEngine createEngine();

    /*制动系统*/
    public abstract Ibrake createBrake();


}
