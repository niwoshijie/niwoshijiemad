package utils;

import android.content.Context;
import android.media.AudioManager;

import base.APP;

public class SoundControl {

    //避开了主线程子线程调用重新实例化，但是每次都要同步判断
    private static volatile SoundControl instance;

    public static SoundControl getInstance() {
        if (instance == null) {
            synchronized (SoundControl.class) {
                if (instance == null) {
                    instance = new SoundControl();
                }
            }
        }
        return instance;
    }


    public static Integer CURRENT_SOUND = 0;
    AudioManager audioManager = (AudioManager) APP.mAppApplication.getSystemService(Context.AUDIO_SERVICE);

    public void setMusicSound(Double volume) {
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Integer volumD = ((Double) (volume * max)).intValue();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumD, AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
    }

    public void stopCurrentVolume() {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0) {
            CURRENT_SOUND = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    public void restartCurrentVolume() {
        if (audioManager.isMusicActive()) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, CURRENT_SOUND, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    /**
     * 获取当前音量
     *
     * @return
     */
    public int getCurrentVolume() {
        int currentSound = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return currentSound;
    }


}
