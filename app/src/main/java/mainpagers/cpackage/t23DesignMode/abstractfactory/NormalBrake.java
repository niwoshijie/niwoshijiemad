package mainpagers.cpackage.t23DesignMode.abstractfactory;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class NormalBrake implements Ibrake{

    @Override
    public void brake() {
        System.out.println("brake: 普通制动");
    }
}
