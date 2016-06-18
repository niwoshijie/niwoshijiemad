package utils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

import base.APP;

/**
 * Created by LiuShao on 2016/5/12.
 */
public class TextToSpeechAll {

    private static TextToSpeech textToSpeech;
    private static volatile TextToSpeechAll textToSpeechAll;
    public  static TextToSpeechAll getTextToSpeechAll() {
        if (textToSpeechAll == null) {
            synchronized (TextToSpeechAll.class) {
                if (textToSpeechAll == null) {
                    textToSpeechAll = new TextToSpeechAll();
                }
            }
        }
        return textToSpeechAll;
    }

    //未装科大讯飞暂未解决,可能是context的原因
    private void initTextToSpeech(){
        if(textToSpeech==null){
            textToSpeech = new TextToSpeech(APP.mAppApplication, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status==TextToSpeech.SUCCESS){
                        int supported = textToSpeech.setLanguage(Locale.CHINA);
                        if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)){

                            final AlertDialog.Builder voiceDialog = new AlertDialog.Builder(APP.mAppApplication);
                            voiceDialog.setTitle("温馨提示");
                            voiceDialog.setMessage("请设置支持中文语音");

                            final Dialog dialog = voiceDialog.create();
                            dialog.show();

                            new Thread(){
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    dialog.dismiss();
                                }
                            }.start();
                        }
                    }
                }
            });
        }
    }



    private HashMap<String, String> textToSpeechMap ;

    public void initVoice(){
        textToSpeechMap = new HashMap<String, String>();
        textToSpeechMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "media");
        textToSpeechMap.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_NOTIFICATION));
        initTextToSpeech();
    }

    public void speakVoice(String message){
        SoundControl.getInstance().stopCurrentVolume();
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, textToSpeechMap);
        textToSpeech.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String utteranceId) {
                if (utteranceId.equals("media")) {
                    SoundControl.getInstance().restartCurrentVolume();
                }
            }
        });
    }

    public void destoryTextToSpeech(){
        if (textToSpeech != null) {
            //停止TextToSpeech
            textToSpeech.stop();
            //释放TextToSpeech占用的资源
            textToSpeech.shutdown();
        }
    }


}
