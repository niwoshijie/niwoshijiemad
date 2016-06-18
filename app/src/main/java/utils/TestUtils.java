package utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import base.APP;

/**
 * Created by LiuShao on 2016/3/7.
 */
public class TestUtils {
    /**
     * 判断service是否运行
     * @return
     */
    private boolean isServiceRunning(Service services){
        ActivityManager manager = (ActivityManager) APP.mAppApplication.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (services.getClass().getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }





}
