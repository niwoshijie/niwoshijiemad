package mainpagers.cpackage.appinformations.settimerclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import base.APP;
import broadcastreceivers.AlarmReceiver;

/**
 * Created by LiuShao on 2016/3/24.
 */
public class SetAlarm {

    private static SetAlarm setAlarm;
    public static SetAlarm getInstance(){
        if(setAlarm == null){
            setAlarm = new SetAlarm();
        }
        return setAlarm;
    }

    private Context context;
    public void setContext(Context context){
        this.context = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    private AlarmManager mAlarmManager;

    Calendar mCalendar = Calendar.getInstance();

    /**
     * @param hour
     * @param time
     * @param isRct ararm 是否是RCT模式
     */
    public void setTime(int hour,int time,boolean isRct){
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.add(Calendar.DAY_OF_MONTH,1);//当前天数加1
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, time);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        openAlarm(hour, time, mCalendar.getTimeInMillis(), isRct);
    }

    private void openAlarm(int hour,int time,long alarmTime,boolean isRct){
        Intent intent = new Intent();
        intent.putExtra("hour", hour);
        intent.putExtra("time", time);
        intent.setClass(context, AlarmReceiver.class);


        if(isRct){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, alarmTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }else {
            PendingIntent pi = PendingIntent.getBroadcast(context, time, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
        }
    }


    public void setListAlarm(List<MyCalendarBean> calendarBeanList){
        for (MyCalendarBean mycalender: calendarBeanList) {

        }
    }





    private TextView tv_showText;
    private int mYear, mMonth, mHour, mMinute, mDay;

    private String[] mDateSplit;
    private String[] mTimeSplit;
    private PendingIntent mPendingIntent;

    public static final String TIMECONTENT = "timecontent";

    public void setAlerm(){

        setTime("15/6/2016","16:51");
        setTime("15/6/2016","16:52");
        setTime("15/6/2016","16:53");
        setTime("15/6/2016","16:55");


    }

    public void setTime(String mDate, String mTime) {
        mDateSplit = mDate.split("/");
        mTimeSplit = mTime.split(":");
        mDay = Integer.parseInt(mDateSplit[0]);
        mMonth = Integer.parseInt(mDateSplit[1]);
        mYear = Integer.parseInt(mDateSplit[2]);
        mHour = Integer.parseInt(mTimeSplit[0]);
        mMinute = Integer.parseInt(mTimeSplit[1]);
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(APP.mAppApplication, AlarmReceiver.class);
        intent.putExtra(TIMECONTENT, mTime);
        mPendingIntent = PendingIntent.getBroadcast(APP.mAppApplication, mMinute, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Calculate notification time
//       Calendar c = Calendar.getInstance();
//       long currentTime = c.getTimeInMillis();
//       long diffTime = calendar.getTimeInMillis() - currentTime;
        //Start alarm using notification time
//       mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,SystemClock.elapsedRealtime() + diffTime, mPendingIntent);

        mAlarmManager.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),mPendingIntent);

    }













}
