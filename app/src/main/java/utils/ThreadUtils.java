package utils;

import android.os.Bundle;

/**
 * Created by LiuShao on 2016/6/14.
 */
public class ThreadUtils {

    public static long getCurrentThreadId() {
        Thread thread = Thread.currentThread();
        return thread.getId();
    }

    /*获取单独线程信息*/
    public static String getThreadSignature() {
        Thread t = Thread.currentThread();
        long l = t.getId();
        String name = t.getName();
        long p = t.getPriority();
        String gname = t.getThreadGroup().getName();
        return ("(Thread):" + name + ":(id)" + l + "(:priority)" + p + ":(group)" + gname);
    }

    public static void sleepForInSecs(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  讲String放进Bundle 中
     * @return
     */
    public static String getStringFromABundle(Bundle b) {
        return b.getString("message");
    }

    /**
     * 获取Bundle的String
     *   @param b
     *   @return
     */
    public static Bundle getStringAsBundle(String message) {
        Bundle b = new Bundle();
        b.putString("message", message);
        return b;
    }


}
