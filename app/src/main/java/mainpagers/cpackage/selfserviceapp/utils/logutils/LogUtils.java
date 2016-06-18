
package mainpagers.cpackage.selfserviceapp.utils.logutils;

import android.util.Log;

/**
 *日志信息管理
 */
public class LogUtils {
    // 是否输出日志的开关
    public static boolean DEBUG = true;

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
            FileLogger.getInstance().addLog(TAG, msg);
        }
    }

    public static void i(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.i(TAG, msg, e);
            FileLogger.getInstance().addLog(TAG, msg, e);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
            FileLogger.getInstance().addLog(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.e(TAG, msg, e);
            FileLogger.getInstance().addLog(TAG, msg, e);
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
            FileLogger.getInstance().addLog(TAG, msg);
        }
    }

    public static void d(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.d(TAG, msg, e);
            FileLogger.getInstance().addLog(TAG, msg, e);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
            FileLogger.getInstance().addLog(TAG, msg);
        }
    }

    public static void v(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.v(TAG, msg, e);
            FileLogger.getInstance().addLog(TAG, msg, e);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
            FileLogger.getInstance().addLog(TAG, msg);
        }
    }

    public static void w(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.w(TAG, msg, e);
            FileLogger.getInstance().addLog(TAG, msg, e);
        }
    }

    public static void println() {
        if (DEBUG) {
            System.out.println();
            FileLogger.getInstance().addLog("", "");
        }
    }

    public static void println(Object msg) {
        if (DEBUG) {
            System.out.println(msg);
            FileLogger.getInstance().addLog("System.out", msg.toString());
        }
    }

    public static void print(Object msg) {
        if (DEBUG) {
            System.out.print(msg);
            FileLogger.getInstance().addLog("System.out", msg.toString());
        }
    }

    public static void printStackTrace(Throwable e) {
        if (DEBUG) {
            e.printStackTrace();
            FileLogger.getInstance().addLog("System.out", e);
        }
    }

    public static void stopFileLogger() {
        FileLogger.getInstance().stop();
    }

    /**
     * 设置日志的存放路径
     * @param fileLogPath
     */
    public static void setFileLogPath(String fileLogPath) {
        FileLogger.getInstance().setLogPath(fileLogPath);
    }

}
