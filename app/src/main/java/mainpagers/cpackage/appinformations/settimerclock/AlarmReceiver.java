package mainpagers.cpackage.appinformations.settimerclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiuShao on 2016/6/15.
 */
public class AlarmReceiver extends BroadcastReceiver{

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String timeReceiver = intent.getStringExtra(SetAlarm.TIMECONTENT);
        Log.d(TAG, "onReceive: " + "接收到的定时时间：" + timeReceiver);
        Log.d(TAG, "onReceive: " + "当前时间：" + getStringDate());
        Toast.makeText(context,"接收时间"+timeReceiver+"当前时间"+getStringDate(),Toast.LENGTH_LONG).show();
    }

    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
