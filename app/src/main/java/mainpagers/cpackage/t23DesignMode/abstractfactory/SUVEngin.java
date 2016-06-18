package mainpagers.cpackage.t23DesignMode.abstractfactory;

import android.util.Log;

/**
 * Created by LiuShao on 2016/6/17.
 */
public class SUVEngin implements IEngine{

    @Override
    public void engine() {
        System.out.println("brake: suv发动机");
    }
}
