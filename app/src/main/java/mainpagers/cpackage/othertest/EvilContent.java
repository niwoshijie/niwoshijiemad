package mainpagers.cpackage.othertest;

import android.content.Context;

import base.APP;

/**
 * Created by LiuShao on 2016/6/30.
 * 得到 别的app的content对象，然后调用app里面的方法，要知道包名
 */
public class EvilContent {

    /*调用其他app里面某个类里面的方法*/
    public void callOtherApp() {
        //载入这个类
        Class clazz = null;
        try {
            Context c = APP.mAppApplication.createPackageContext("packagename", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            clazz = c.getClassLoader().loadClass("packagename.Main");
            //新建一个实例
            Object owner = clazz.newInstance();
            //获取print方法，传入参数并执行
            Object obj = clazz.getMethod("print", String.class).invoke(owner, "Hello");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
