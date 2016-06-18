package mainpagers.cpackage.t23DesignMode.singleInstance;

/**
 * Created by LiuShao on 2016/5/19.
 */
public class SingleInstance3 {
private SingleInstance3(){}
    public static SingleInstance3 getSingleInstance(){
        return SingletonHolder.sInstance;
    }
    /**
     * 静态内部类
     */
    private static class SingletonHolder{
            private static final SingleInstance3 sInstance = new SingleInstance3();
    }

}
