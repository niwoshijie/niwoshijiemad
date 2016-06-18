package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import base.APP;
import liushaobo.mad.R;

public class WeiChatVideoPager extends BaseWeiChatPager{

    private VideoView weichat_video_view;


    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;

    public WeiChatVideoPager(Context context) {
        super(context);
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.wei_chat_video, null);
        weichat_video_view = (VideoView) view.findViewById(R.id.weichat_video_view);

        return view;
    }

    @Override
    public void initData(String videoUri) {
        System.gc();
        File file = new File(videoUri);
        if (!file.exists()) {
            return;
        }

        weichat_video_view.setVideoURI(Uri.parse(videoUri));

        weichat_video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                weichat_video_view.requestFocus();
                weichat_video_view.requestFocusFromTouch();
                weichat_video_view.start();
                if (weiChatPagerReceive != null) {
                    weiChatPagerReceive.stop();
                }
            }
        });

        weichat_video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (weiChatPagerReceive != null) {
                    weiChatPagerReceive.start();
                }
            }
        });

        weichat_video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    Toast.makeText(context, "视频服务异常", Toast.LENGTH_SHORT).show();
                } else if (what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                }
                return true;
            }
        });

        mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                    //失去焦点之后的操作
                    if(weichat_video_view.isPlaying()){
                        weichat_video_view.pause();
                    }
                }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    //获得焦点之后的操作
                }
            }
        };
    }


    private AudioManager mAudioMgr;
    private MediaPlayer mediaPlayer;

    private void requestAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr == null)
            mAudioMgr = (AudioManager) APP.mAppApplication.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioMgr != null) {
            int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            }
        }
    }

    private void abandonAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr != null) {

            mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);

            mAudioMgr = null;
        }
    }


}
