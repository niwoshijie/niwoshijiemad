package mainpagers.cpackage.mediaFocusTest;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.VideoView;

import utils.TextToSpeechAll;

/**
 * Created by LiuShao on 2016/3/14.
 */
public class MediaFoucsChangeListener implements AudioManager.OnAudioFocusChangeListener{

    private final String mediaTAG = "mediafocus";

    private MediaPlayer mediaPlayer;
    private TextToSpeechAll textToSpeechAll;
    private VideoView videoView;

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setTextToSpeechAll(TextToSpeechAll textToSpeechAll) {
        this.textToSpeechAll = textToSpeechAll;
    }
    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.e("音频焦点", "获得音频焦点");
                // 获得音频焦点
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                // 还原音量
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("音频焦点", " 长久的失去音频焦点");
                // 长久的失去音频焦点，释放MediaPlayer
//                                stop();

                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // 暂时失去音频焦点，暂停播放等待重新获得音频焦点
                Log.e("音频焦点", "暂时失去音频焦点1");
//                                 pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // 失去音频焦点，无需停止播放，降低声音即可
                Log.e("音频焦点", "暂时失去音频焦点2");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.setVolume(0.2f, 0.2f);
                }
                break;
        }
    }



    /**
     * 暂停
     */
    protected void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 重新播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
    }

    /**
     * 停止播放
     */
    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    protected  void restart(){
        Log.e("mediaTAG", "获得音频焦点");
        // 获得音频焦点
        if(mediaPlayer!=null){
            Log.e(mediaTAG,"mediaPlayer获得音频焦点");
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            // 还原音量
            mediaPlayer.setVolume(1.0f, 1.0f);
        }

        // 获得音频焦点
        if(videoView!=null){
            Log.e(mediaTAG,"mediaPlayer获得音频焦点");
            if (!videoView.isPlaying()) {
                videoView.start();
            }
            // 还原音量
            videoView.start();
        }
    }


}
