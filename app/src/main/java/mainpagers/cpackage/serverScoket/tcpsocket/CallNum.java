package mainpagers.cpackage.serverScoket.tcpsocket;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import base.APP;
import liushaobo.mad.R;
import utils.CommonUtils;
import utils.HandleMessageUtils;
import utils.TextToSpeechAll;
/**
 * Created by LiuShao on 2016/3/1.
 */
public class CallNum {

    public static CallNum callNum;
    public static CallNum callNumInstance() {
        if (callNum == null) {
            callNum = new CallNum();
            callNum.init();
        }
        return callNum;
    }

    /**
     * 连接界面显示的service
     */
    public class ConService implements ServiceConnection {
        //获取连接
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            callTextService = (CallTextService) service;
        }
        //失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }

    String keyCodeStr = "";
    String timeleCodeStr = "";//暂时存放的信息
    long lastTime = 0;
    int timeCount;

    private CallTextService callTextService;
    private ConService conn;
    AudioManager mAudioManager = (AudioManager) APP.mAppApplication.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    private SocketControl socketControl;

    public static String mainIpAddress;
    private String winName;
    private String localIpAdress = CommonUtils.getIpAdress();

    public void handKeyCode(int keyCode, KeyEvent event) {
        Log.e("keycode", keyCode + "");
        if (keyCode >= 144 && keyCode <= 153) {// 数字键
            keyCodeStr += (keyCode - 144) + " ";
            timeleCodeStr = keyCodeStr;//叫号信息转移
        } else if (keyCode == 158) {
            keyCodeStr += ".";
            timeleCodeStr = keyCodeStr;//叫号信息转移
        } else if (keyCode == 155) {//按*键表示
            keyCodeStr += "-";
            timeleCodeStr = keyCodeStr;//叫号信息转移
        } else if (keyCode == 160) { //enter键
            if (keyCodeStr.equals("1 1 2 2 3 3 ")) {
                showSettingDialog();
            } else {
                if (!needOpenCallServer) {
                    if (isSingleDev==1) {
                        speakVoice();
                    } else {

                        long current = SystemClock.elapsedRealtime();
                        long intervalTime = Math.abs(current - lastTime);
                        //一秒钟最多响应两次
                        if (intervalTime < 1000) {
                            timeCount++;
                            if (timeCount < 2) {
                                toSpeak();
                            }
                        } else {
                            timeCount = 0;
                            toSpeak();
                        }
                        lastTime = current;
                    }
                }
            }
            keyCodeStr = "";
        } else if (keyCode == 67) {// 按删除键都取消
            timeleCodeStr = "";
        } else if (keyCode == 156) {//声音减
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
        } else if (keyCode == 157) {//声音加
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (builder != null && builder.isShowing()) {
                builder.dismiss();
            }
        }
    }

    private static String mainIpMessage;

    private boolean needOpenCallServer = true;

    private int isMianServer = 0;//0表示不是服务器

    private int isSingleDev = 1;

    /**
     * 初始化socket
     */
    public void initSocketController() {
        socketControl = socketControl.getInstance();
        socketControl.setServerIpAddress(mainIpAddress);
        isMianServer = Integer.parseInt(CallNumCache.getIsMainServer());
        if(isMianServer==0){
            socketControl.setIsMainServer(false);
        }else{
            socketControl.setIsMainServer(true);
        }
        socketControl.connectSocket();
    }

    /**
     * 分割字符串，获取ip和窗口名称
     */
    public void getIpAddressWinName() {
        String[] ipWinName = mainIpMessage.split(",");
        if (ipWinName.length == 2) {
            mainIpAddress = ipWinName[0];
            winName = ipWinName[1];
        }
    }

    public void init() {
        winName = "";
        isSingleDev = Integer.parseInt(CallNumCache.getIsSingleDevice());

        mainIpMessage = CallNumCache.getMainIpadress();
        if (!TextUtils.isEmpty(mainIpMessage)) {
            getIpAddressWinName();
            initTextToSpeech();
            initService();
        }

        if(isSingleDev ==1){ //单个设备
            initTextToSpeech();
            initService();

        }else{
            if (!TextUtils.isEmpty(mainIpMessage)) {
                initSocketController();
            } else {

            }
        }
        needOpenCallServer = false;
    }

    private Dialog builder;

    //展示显示设置的界面
    private void showSettingDialog() {

        if (callTextService != null) {
            callTextService.hideMessageWindow();
        }

        View view =View.inflate(APP.mAppApplication,R.layout.c_server_setting_layout, null);
        final RadioGroup radiogroup_server_type = (RadioGroup) view.findViewById(R.id.radiogroup_server_type);
        final RadioGroup radiogroup_fuwu_type = (RadioGroup) view.findViewById(R.id.radiogroup_fuwu_type);
        final LinearLayout setting_fuwu_type = (LinearLayout) view.findViewById(R.id.setting_fuwu_type);
        final LinearLayout setting_ip_adress = (LinearLayout) view.findViewById(R.id.setting_ip_adress);
        final EditText mainserver_ip_input = (EditText) view.findViewById(R.id.mainserver_ip_input);
        final EditText et_ipname = (EditText) view.findViewById(R.id.et_ipname);
        final EditText et_socket_port = (EditText) view.findViewById(R.id.et_socket_port);
        final TextView tv_local_ip = (TextView) view.findViewById(R.id.tv_local_ip);
        tv_local_ip.setText("本机IP:" + localIpAdress);

        boolean savedServerBoolean;
        String isServer = CallNumCache.getIsMainServer();
        if(isServer.equals("1")){
            savedServerBoolean = true;
        }else {
            savedServerBoolean = false;
        }

        String socketPort = CallNumCache.getSocketPortNum();

        int isSingleDevsave = Integer.parseInt(CallNumCache.getIsSingleDevice());
        mainIpMessage = CallNumCache.getMainIpadress();

        if (!TextUtils.isEmpty(mainIpMessage)) {
            getIpAddressWinName();
            et_ipname.setText(winName);
        }

        et_socket_port.setText(socketPort);
        if (isSingleDevsave==1) {
            radiogroup_server_type.check(R.id.single_device);
        } else {
            setting_fuwu_type.setVisibility(View.VISIBLE);
            radiogroup_server_type.check(R.id.multi_device);
            if (savedServerBoolean) {
                radiogroup_fuwu_type.check(R.id.main_userid);
            } else {
                radiogroup_fuwu_type.check(R.id.device_id);
                setting_ip_adress.setVisibility(View.VISIBLE);
                mainserver_ip_input.setText(mainIpAddress);
            }
        }

        Button button_ip_setting_ok = (Button) view.findViewById(R.id.button_ip_setting_ok);
        Button button_out = (Button) view.findViewById(R.id.button_out);

        needOpenCallServer = true;

        radiogroup_server_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.single_device:
                        setting_fuwu_type.setVisibility(View.GONE);
                        break;
                    case R.id.multi_device:
                        setting_fuwu_type.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        radiogroup_fuwu_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_userid:
                        setting_ip_adress.setVisibility(View.GONE);
                        break;
                    case R.id.device_id:
                        setting_ip_adress.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        button_ip_setting_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radiogroup_server_type.getCheckedRadioButtonId()==R.id.single_device){
                    isSingleDev = 1;
                }else{
                    isSingleDev = 0;
                }

                if(radiogroup_fuwu_type.getCheckedRadioButtonId()==R.id.main_userid){
                    isMianServer = 1;
                }else{
                    isMianServer = 0;
                }

                if (socketControl != null) {
                    socketControl.disconnectSocket();
                    socketControl = null;
                }

                if (isMianServer==1) {
                    mainIpAddress = localIpAdress;
                } else {
                    String mainServerIp = mainserver_ip_input.getText().toString();
                    mainIpAddress = mainServerIp;
                }
                winName = et_ipname.getText().toString();
                CallNumCache.putDevicetypeConstant(String.valueOf(isMianServer));//是否是服务器
                CallNumCache.putIsSingleDevice(String.valueOf(isSingleDev));
                CallNumCache.putMainIpadress(mainIpAddress + "," + winName);
                CallNumCache.putSocketPortNum(et_socket_port.getText().toString());
                builder.dismiss();
                reLoadApp();
            }
        });

        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                if (!TextUtils.isEmpty(CallNumCache.getMainIpadress())) {
                    needOpenCallServer = false;
                }
            }
        });
        builder = new AlertDialog.Builder(APP.mAppApplication).setTitle("叫号设置").setView(view).setCancelable(false).create();
        builder.show();
    }

    /**
     * 重新设置IP之后重启设备
     */
    private void reLoadApp(){

        Intent intent = new Intent();
        String packgeName = APP.mAppApplication.getPackageName();
        // 参数1：包名，参数2：程序入口的activity
        intent.setClassName(packgeName, packgeName + ".MainActivity");
        PendingIntent restartIntent = PendingIntent.getActivity(APP.mAppApplication.getApplicationContext(), -1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 1秒钟后重启应用
        AlarmManager mgr = (AlarmManager) APP.mAppApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private HashMap<String, String> textToSpeechMap = new HashMap<>();
    private List<String> ontimeList = new ArrayList<>();

    private void initTextToSpeech() {
        textToSpeechMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
//        TextToSpeechAll.getTextToSpeechAll().setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
//            @Override
//            public void onUtteranceCompleted(String utteranceId) {
//                if (utteranceId.equals("UniqueID")) {
//                    ontimeList.remove(0);
//                    if (ontimeList.size() > 0) {
//                        HandleMessageUtils.getInstance().sendHandler(SENDTOSHOW, mhandler, ontimeList.get(0));
//                    }
//                }
//            }
    }

    private void initService() {
        if (conn == null) {
            //绑定叫号服务
             Intent intent = new Intent(APP.mAppApplication, CallNumBerShow.class);
            conn = new ConService();
            isBind = APP.mAppApplication.bindService(intent, conn, Context.BIND_AUTO_CREATE);
            //服务端口接收到信息之后处理
            ClientSide.getInstance().setOnreceivedMsg(new ClientSide.OnreceivedMsg() {
                @Override
                public void onrecedMsg(String msg) {
                    handReceivedMeg(msg);
                }
            });

            //发送失败自己播放
            ClientSide.getInstance().setConFaile(new ClientSide.ConFaile() {
                @Override
                public void received(String msgs) {
                    handReceivedMeg(msgs);
                }
            });

            } else {

            }
    }



    private void handReceivedMeg(String msg){
        if(msg!=null&&!TextUtils.isEmpty(msg)){
            String[] ipWinName = msg.split(",");
            String message = ipWinName[0];
            String sourseWinName = ipWinName[1];
            String onTimeString = message;

            String stringmsg = handVoice(message,sourseWinName);
            ontimeList.add(ontimeList.size(), stringmsg);

            onTimeString = shutDisplay(onTimeString,sourseWinName);
            playText(onTimeString);
        }
    }


    private static final int SENDTOSHOW = 0x211231;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENDTOSHOW:
                    String message = (String) msg.obj;
                    callTextService.callMethodInService(message);
                    break;
            }
        }
    };

    /**
     * 单机版的直接叫号
     */
    private void speakVoice() {
        //显示处理
        String message = handVoice(timeleCodeStr,winName);
        ontimeList.add(ontimeList.size(), message);

        //处理叫号
        String callMessage = shutDisplay(timeleCodeStr,winName);
        playText(callMessage);
    }

        /**
         * 处理叫号信息
         * @param timeleCodeStr
         * @return
         */
        private String shutDisplay(String timeleCodeStr,String sourseWinName) {
            String callMessage = handBlank(timeleCodeStr);

            if (callMessage.contains(".")) {
                if (callMessage.endsWith(".")) {
                    callMessage = callMessage.substring(0, callMessage.length() - 1);
                }

                callMessage = hand2StirngPoint(callMessage);
                Log.e("clientport", callMessage);
                callMessage = callMessage.replaceAll("\\.", ";");
                callMessage = "请" + callMessage + "座位号顾客来" + sourseWinName + "取餐";
            } else if (callMessage.contains("-")) {
                if (callMessage.endsWith("-")) {
                    callMessage = callMessage.substring(0, callMessage.length() - 1);
                }
                callMessage = hand2Stirng(callMessage);
                Log.e("clientport", callMessage);
                callMessage = callMessage.replaceAll("-", ";");
                callMessage = "请" + callMessage + "号顾客来" + sourseWinName + "取餐";
            } else {
                callMessage = "请" + callMessage + "号顾客来" + sourseWinName + "取餐";
            }
            return callMessage;
        }

        /**
         * 处理带2的字符串
         * @param stringToCall
         */
        private String hand2StirngPoint(String stringToCall){
            String result ="";
            String[]  stringArr = stringToCall.split("\\.");
            for(int i=0;i<stringArr.length;i++){
                if(stringArr[i].startsWith("2")){
                    stringArr[i] =";"+stringArr[i];
                }
                stringArr[i] =stringArr[i]+".";
                result += stringArr[i];
            }
//            if (result.endsWith(".")) {
//                result = result.substring(0, result.length() - 1);
//            }
            return result;
        }

    private String hand2Stirng(String stringToCall){
        String result ="";
        String[]  stringArr = stringToCall.split("-");
        for(int i=0;i<stringArr.length;i++){
            if(stringArr[i].startsWith("2")){
                stringArr[i] =";"+ stringArr[i];
            }
            stringArr[i] =stringArr[i]+"-";
            result += stringArr[i];
        }
        if (result.endsWith("-")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private void toSpeak() {
        if (!TextUtils.isEmpty(timeleCodeStr)) {
//           final String message = handVoice(timeleCodeStr);
            HandleMessageUtils.getInstance().runInThread(new Runnable() {
                @Override
                public void run() {
                    socketControl.sendData(timeleCodeStr+","+winName);
                }
            });
        }
    }


    private void playText(String message) {
        TextToSpeechAll.getTextToSpeechAll().speakVoice(message);
        if (ontimeList.size() == 1) {
            HandleMessageUtils.getInstance().sendHandler(SENDTOSHOW, mhandler, ontimeList.get(0));
        }
    }

    /**
     * 处理空格
     * @param string
     * @return
     */
    private String handBlank(String string){
        String voices;
        int starLocation = string.indexOf("-");
        int pointLocation = string.indexOf(".");
        if (starLocation != -1 || pointLocation != -1) {
            voices = string.replaceAll(" ", "");
        } else {
            if (string.length() < 10) {
                voices = string.replaceAll(" ", "");
            } else {
                voices = string;
            }
        }
        return voices;
    }

    private String handVoice(String voiceString,String sourseWinName) {
        String voices = handBlank(voiceString);
        if (voices.contains(".")) {
            if (voices.endsWith(".")) {
                voices = voices.substring(0, voices.length()-1);
            }
            voices = voices.replaceAll("\\.", ";");
            voices = "请" + voices + "座位号顾客来" + sourseWinName + "取餐";
        } else if (voices.contains("-")) {
            if (voices.endsWith("-")) {
                voices = voices.substring(0, voices.length() - 1);
            }
            voices = voices.replaceAll("-", ";");
            voices = "请" + voices + "号顾客来" + sourseWinName + "取餐";
        } else {
            voices = "请" + voices + "号顾客来" + sourseWinName + "取餐";
        }
        return voices;
    }


    public void releaseSourse() {
//        unbind();
        TextToSpeechAll.getTextToSpeechAll().destoryTextToSpeech();
        if (socketControl != null) {
            socketControl.disconnectSocket();
        }
    }

    private boolean isBind = false;

    //解除服务
//    public void unbind() {
//        if (conn!=null && isBind) {
//            BaseActivity.getActivity().unbindService(conn);
//        }
//    }

}
