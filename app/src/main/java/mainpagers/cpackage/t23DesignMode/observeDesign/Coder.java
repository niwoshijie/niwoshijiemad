package mainpagers.cpackage.t23DesignMode.observeDesign;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/5/25.
 */
public class Coder implements Observer{

    public String name;

    public Coder(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.e("HI you are best","");
    }

    @Override
    public String toString() {
        return "码农也牛逼"+name;
    }
}
