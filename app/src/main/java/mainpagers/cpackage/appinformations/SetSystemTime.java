package mainpagers.cpackage.appinformations;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import base.APP;

/**
 * Created by LiuShao on 2016/3/23.
 * 设置系统时间
 *
 */
public class SetSystemTime {

    private static SetSystemTime setSystemTime;

    public static SetSystemTime getInstance() {
        if (setSystemTime == null) {
            setSystemTime = new SetSystemTime();
        }
        return setSystemTime;
    }

    static final String TAG = "SystemDateTime";

    public void setDateTime(int year, int month, int day, int hour, int minute) {
        try {
            requestPermission();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month - 1);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            long when = c.getTimeInMillis();
            if (when / 1000 < Integer.MAX_VALUE) {
                SystemClock.setCurrentTimeMillis(when);
            }
            long now = Calendar.getInstance().getTimeInMillis();
            if (now - when > 1000) {
                throw new IOException("failed to set Date.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //crtl alt t 调用try catch

    public void setDate(int year, int month, int day) {

        try {
            requestPermission();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            long when = c.getTimeInMillis();

            if (when / 1000 < Integer.MAX_VALUE) {
                SystemClock.setCurrentTimeMillis(when);
            }

            long now = Calendar.getInstance().getTimeInMillis();
            //Log.d(TAG, "set tm="+when + ", now tm="+now);

            if (now - when > 1000)
                throw new IOException("failed to set Date.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTime(int hour, int minute) {

        try {
            requestPermission();

            Calendar c = Calendar.getInstance();

            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            long when = c.getTimeInMillis();

            if (when / 1000 < Integer.MAX_VALUE) {
                SystemClock.setCurrentTimeMillis(when);
            }

            long now = Calendar.getInstance().getTimeInMillis();
            //Log.d(TAG, "set tm="+when + ", now tm="+now);

            if (now - when > 1000) {
                throw new IOException("failed to set Time.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermission() {
        try {
            createSuProcess("chmod 666 /dev/alarm").waitFor();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Process createSuProcess() {
            File rootUser = new File("/system/xbin/ru");
            if (rootUser.exists()) {
                try {
                    return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    return Runtime.getRuntime().exec("su");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    public Process createSuProcess(String cmd) {
        DataOutputStream os = null;
        Process process = createSuProcess();

        try {
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit $?\n");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
        }
        return process;
    }


    public void setTime(int year,int month,int day,int hour,int minute,int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        AlarmManager am = (AlarmManager) APP.mAppApplication.getSystemService(Context.ALARM_SERVICE);
        am.setTime(c.getTimeInMillis());
    }


}
