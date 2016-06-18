package mainpagers.cpackage.selfserviceapp.utils;

import android.os.Environment;

/**
 * Created by LiuShao on 2016/4/6.
 */
public class SdCardUtils {

    /**
     * 判断sdcard是否可用
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }



}
