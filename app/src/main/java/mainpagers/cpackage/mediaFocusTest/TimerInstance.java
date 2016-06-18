package mainpagers.cpackage.mediaFocusTest;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LiuShao on 2016/5/18.
 */
public class TimerInstance {

//    private static volatile TimerInstance timerInstance;
//    public  static TimerInstance getT() {
//        if (timerInstance == null) {
//            synchronized (TimerInstance.class) {
//                if (timerInstance == null) {
//                    timerInstance = new TimerInstance();
//                }
//            }
//        }
//        return timerInstance;
//    }

    private Timer showTimer;
    private int showTime = 5;
    private TimerTask showTimerTask = new TimerTask() {
        @Override
        public void run() {
            showTime--;
            if (showTime == 0) {
                execSuCmd(getDeviceString());
                stopTimer();
            }
        }
    };

    public void startTimer() {
        showTimer = new Timer();
        showTimer.schedule(showTimerTask, 1000, 1000);
    }

    public void stopTimer() {
        if (null != showTimer) {
            showTimer.cancel();
            showTimer = null;
        }
    }

    /**
     * 重置时间
     */
    public void setTime(int timw) {
        showTime = timw;
    }

    private String deviceString;

    public String getDeviceString() {
        return deviceString;
    }

    public void setDeviceString(String deviceString) {
        this.deviceString = deviceString;
    }

    private static void execSuCmd(String cmd) {
        Process process = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            int aa = process.waitFor();
            is = new DataInputStream(process.getInputStream());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String out = new String(buffer);
            Log.i("tag", out + aa);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }




}
