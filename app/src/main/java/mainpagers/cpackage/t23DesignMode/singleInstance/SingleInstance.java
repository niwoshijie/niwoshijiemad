package mainpagers.cpackage.t23DesignMode.singleInstance;


/**
 * Created by LiuShao on 2016/5/10.
 */
public class SingleInstance {


    //避开了主线程子线程调用重新实例化，但是每次都要同步判断
    private static volatile SingleInstance instance;
    public static SingleInstance getInstance() {
        if (instance == null) {
            synchronized (SingleInstance.class) {
                if (instance == null) {
                    instance = new SingleInstance();
                }
            }
        }
        return instance;
    }


}
