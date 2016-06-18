package mainpagers.cpackage.appinformations.settimerclock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LiuShao on 2016/6/3.
 */
public class TimerToSleep {


    private static TimerToSleep timerToSleep = null;
    private TimerToSleep() {}
    public static TimerToSleep getTimerToSleep() {
        if (timerToSleep == null) {
            synchronized (TimerToSleep.class) {
                if (timerToSleep == null) {
                    timerToSleep = new TimerToSleep();
                }
            }
        }
        return timerToSleep;
    }


    /**
     * A20关机时间不正确情况下加一个线程
     */
    private Timer showOnTimer;
    private Timer showOffTimer;
    private int showOnTime = 1;//开机时间
    private int showOffTime = 2;//关机时间


//    setToff("echo on>/sys/power/state",60);setToff("echo mem>/sys/power/state",20);

//    Log.e("time", "设置开关机时间：onh：" + onh.byteValue() + " onm:" + onm.byteValue() + " offh：" + offh.byteValue() + "offm：" + offm.byteValue());

    public void setTime(int onH,int onM,int offH,int offM){
        showOnTime = onH*60*60+onM*60;
        showOffTime = offH*60*60+offM*60;
        startTimer();
    }

    private TimerTask showTimerTask = new TimerTask() {
        @Override
        public void run() {
            showOnTime--;
            if (showOnTime == 0) {
//                PowerOffTool.getPowerOffTool().execSuCmd("echo on>/sys/power/state");
//                SoundControl.restartCurrentVolume();
                stopOnTimer();
            }
        }
    };

    private TimerTask offTimerTask = new TimerTask() {
        @Override
        public void run() {
            showOffTime--;
            if (showOffTime == 0) {
//                PowerOffTool.getPowerOffTool().execSuCmd("echo mem>/sys/power/state");
//                SoundControl.stopCurrentVolume();
                stopOffTimer();
            }
        }
    };

    private void startTimer() {
        showOnTimer = new Timer();
        showOnTimer.schedule(showTimerTask, 1000, 1000);
        showOffTimer = new Timer();
        showOffTimer.schedule(offTimerTask,1000,1000);
    }

    private void stopOnTimer() {
        if (null != showOnTimer) {
            showOnTimer.cancel();
            showOnTimer = null;
        }
    }

    private void stopOffTimer() {
        if (null != showOnTimer) {
            showOnTimer.cancel();
            showOnTimer = null;
        }
    }

    public void closeAll(){
        stopOnTimer();
        stopOffTimer();
    }

}
