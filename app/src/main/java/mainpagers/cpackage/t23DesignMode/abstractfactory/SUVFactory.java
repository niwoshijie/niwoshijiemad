package mainpagers.cpackage.t23DesignMode.abstractfactory;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class SUVFactory extends CarFactory{

    @Override
    public ITire createItire() {
        return new SUVTire();
    }

    @Override
    public IEngine createEngine() {
        return new SUVEngin();
    }

    @Override
    public Ibrake createBrake() {
        return new SuvBrake();
    }
}
