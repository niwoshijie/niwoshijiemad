package mainpagers.cpackage.t23DesignMode.singleInstance;

/**
 * Created by LiuShao on 2016/5/19.
 */
public class SingleInstance2 {
    private static SingleInstance2 singleInstance2 = null;
    private SingleInstance2(){}
    public static SingleInstance2 getSingleInstance2(){
        if(singleInstance2 == null){
            synchronized (SingleInstance2.class){
                if(singleInstance2 == null){
                    singleInstance2 = new SingleInstance2();
                }
            }
        }
        return singleInstance2;
    }

}
