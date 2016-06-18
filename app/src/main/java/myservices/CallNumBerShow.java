package myservices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import liushaobo.mad.R;
import utils.HandleMessageUtils;

public class CallNumBerShow extends Service {
    private Context context;
    private WindowManager.LayoutParams params  = new WindowManager.LayoutParams();
    private WindowManager wm;
    private TextView myTextView;
    private HandleMessageUtils handleMessageUtils;
    private SpannableString msp = null;

    private View view;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(" ", "onBind" + "");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("服务", "onCreate" + "");
        context = getApplication();
        handleMessageUtils = new HandleMessageUtils();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        params.height =  1;
        params.width = 1;
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE; //使用高优先级的窗体，要权限
        params.gravity = Gravity.CENTER;

        view = LayoutInflater.from(context).inflate(R.layout.call_layout_show,null);
        myTextView = (TextView) view.findViewById(R.id.tv_call_detial);
        myTextView.setText("欢迎使用云标叫号");
//      wm.addView(myTextView, params);
        wm.addView(view, params);
        timerToDismiss();
    }

    private class MyBinder extends Binder implements CallTextService {
        @Override
        public void callMethodInService(String textNum) {

            if(view!=null){
                textNum = textNum.replaceAll(" ","");
                msp = highlight(textNum);
                myTextView.setText(msp);
                myTextView.setBackgroundColor(Color.parseColor("#bb000000"));
                params.height =  ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                wm.updateViewLayout(view,params);
                timerToDismiss();
            }
        }

        @Override
        public void hideMessageWindow() {
            myTextView.setText("");
            params.height =  1;
            params.width = 1;
            view.setBackgroundColor(Color.TRANSPARENT);
            wm.updateViewLayout(view,params);
        }
    }

    /**
     * 关键字高亮显示
     * @param
     */
    public SpannableString highlight(String message){
        message = message.replaceAll(";",",");
        SpannableString spannable = new SpannableString(message);
        CharacterStyle span = null;
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(message);
        while (m.find()) {
            span = new ForegroundColorSpan(Color.RED);//需要标红
            spannable.setSpan(span, m.start(),  m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(2.0f), m.start(),m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();
    }

    private Timer timerToDismiss;
    private int timer = 20;//转入屏保的时间

    /**
     * 设定取消时间
     */
    public void timerToDismiss() {
        if (timerToDismiss != null) {
            timerToDismiss.cancel();
            timerToDismiss = null;
            timer = 20;
        }

        if (timerToDismiss == null) {
            timerToDismiss = new Timer();
            timerToDismiss.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer--;
                    if (timer == 0) {
                        handleMessageUtils.sendHandler(DISSMISSTEXT, texthandler, "");
                        timer = 20;
                    }
                }
            }, 1000, 1000);
        }
    }

    private final int DISSMISSTEXT = 250250;
    private Handler texthandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DISSMISSTEXT:

                    myTextView.setText("");
                    params.height =  1;
                    params.width = 1;
                    view.setBackgroundColor(Color.TRANSPARENT);
                    wm.updateViewLayout(view,params);

                    break;
            }
        }
    };

}
