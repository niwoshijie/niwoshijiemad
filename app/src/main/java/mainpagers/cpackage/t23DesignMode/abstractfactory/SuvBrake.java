package mainpagers.cpackage.t23DesignMode.abstractfactory;

import android.util.Log;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class SuvBrake implements Ibrake{
    @Override
    public void brake() {
        System.out.println("brake: suv制动");
    }
}
