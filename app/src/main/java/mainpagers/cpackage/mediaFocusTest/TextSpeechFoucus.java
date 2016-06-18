package mainpagers.cpackage.mediaFocusTest;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;

import utils.TextToSpeechAll;

/**
 * Created by LiuShao on 2016/3/12.
 */
public class TextSpeechFoucus implements AudioManager.OnAudioFocusChangeListener{

    String voiceToSpeak;
    HashMap map;
    int queueType;

    public TextSpeechFoucus(String voiceToSpeak, HashMap map,int queueType){
        this.map = map;
        this.voiceToSpeak = voiceToSpeak;
        this.queueType = queueType;
    }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onAudioFocusChange(int focusChange) {
            Log.e("音频焦点", "textToSpeech" + focusChange);
            TextToSpeechAll.getTextToSpeechAll().speakVoice(voiceToSpeak);

            AudioAttributes.Builder builder =  new AudioAttributes.Builder() ;
            builder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
//            TextToSpeechAll.getInstance().textToSpeech.setAudioAttributes(builder.build());

        }

}
