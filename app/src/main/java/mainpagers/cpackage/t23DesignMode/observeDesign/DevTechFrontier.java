package mainpagers.cpackage.t23DesignMode.observeDesign;

import java.util.Observable;

/**
 * Created by Administrator on 2016/5/25.
 */
public class DevTechFrontier extends Observable{

    public void postNewPublication(String content) {
        //标志状态或者内容发生变化
        setChanged();
        //通知所有的观察者
        notifyObservers();
    }





}
