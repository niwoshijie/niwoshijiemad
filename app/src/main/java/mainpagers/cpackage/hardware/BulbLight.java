package mainpagers.cpackage.hardware;

import android.os.IBinder;

import java.lang.reflect.Method;

import liushaobo.mad.IHardwareService;

/**
 * Created by LiuShao on 2016/6/14.
 */
public class BulbLight {

    /**
     * 设置闪光灯的开启和关闭
     */
    public void setFlashlightEnabled(boolean isEnable) {
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{"hardware"});
            IHardwareService localhardwareservice = IHardwareService.Stub.asInterface(binder);
            localhardwareservice.setFlashlightEnabled(isEnable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
