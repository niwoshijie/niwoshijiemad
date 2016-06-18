package broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import utils.DialogUtil;

/**
 * Created by LiuShao on 2016/3/24.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String hour = intent.getStringExtra("hour");
        String time = intent.getStringExtra("time");
        DialogUtil.dialogBuilder(context, "时间", hour + ":" +time);
    }

}
