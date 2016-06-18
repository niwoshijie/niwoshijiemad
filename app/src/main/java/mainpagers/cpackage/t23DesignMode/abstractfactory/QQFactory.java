package mainpagers.cpackage.t23DesignMode.abstractfactory;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class QQFactory extends CarFactory{
    @Override
    public ITire createItire() {
        return new NormalTire();
    }

    @Override
    public IEngine createEngine() {
        return new NormalEngin();
    }

    @Override
    public Ibrake createBrake() {
        return new NormalBrake();
    }
}
