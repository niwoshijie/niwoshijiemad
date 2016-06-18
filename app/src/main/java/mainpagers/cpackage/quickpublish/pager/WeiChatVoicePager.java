package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import liushaobo.mad.R;

public class WeiChatVoicePager extends BaseWeiChatPager{

    private ImageView imageview;

    @ViewInject(R.id.iv_voice_chat_iamge1)
    private ImageView iv_voice_chat_iamge1;
    @ViewInject(R.id.iv_voice_chat_iamge2)
    private ImageView iv_voice_chat_iamge2;

    public WeiChatVoicePager(Context context) {
        super(context);
    }


    @Override
    public View initView() {
      View view = View.inflate(context, R.layout.wei_chat_voice_layout, null);
      imageview = (ImageView) view.findViewById(R.id.iv_voice_chat_iamge);
        x.view().inject(this,view);
      return view;
    }

    private String path1 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/tmp/aa.mp3";
    private String path2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/tmp/bb.mp3";

    @Override
    public void initData(final String voicePath) {
        System.gc();


        iv_voice_chat_iamge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(path1);
            }
        });

        iv_voice_chat_iamge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(path2);
            }
        });

    }

    private MediaPlayer mediaPlayer;
    private  AudioManager am;

    private void play(final String voicePath){
        final AnimationDrawable voiceAnimation;
        imageview.setImageResource(R.drawable.voice_from_icon);
        voiceAnimation = (AnimationDrawable)imageview.getDrawable();
        try {
        File file=new File(voicePath);
        //点击声音文件播放声音
        if(file.exists() && file.length() > 0){
            mediaPlayer = new MediaPlayer();
            am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            // 获得音频焦点
                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                            // 还原音量
                            mediaPlayer.setVolume(1.0f, 1.0f);

                    break;
                    case AudioManager.AUDIOFOCUS_LOSS:

                    // 长久的失去音频焦点，释放MediaPlayer
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                    break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:

                    // 展示失去音频焦点，暂停播放等待重新获得音频焦点
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.pause();

                    break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // 失去音频焦点，无需停止播放，降低声音即可
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.setVolume(0.6f, 0.6f);
                    }
                    break;

                }
            }
        }
                    , AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
            // 设定CUP锁定
            mediaPlayer.setWakeMode(context,PowerManager.PARTIAL_WAKE_LOCK);
            voiceAnimation.start();
            mediaPlayer.setDataSource(voicePath);
            // 设置音频流的类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (weiChatPagerReceive != null) {
                        weiChatPagerReceive.stop();
                    }
                    mediaPlayer.start();
                }
            });

          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    voiceAnimation.stop();
                    if (weiChatPagerReceive != null) {
                        weiChatPagerReceive.start();
                    }
                    imageview.setImageResource(R.mipmap.voice_1);
                    mediaPlayer.release();
                }
            });



        }else {
            voiceAnimation.stop();
            imageview.setImageResource(R.mipmap.voice_1);
        }



        }catch(Exception e){
            e.printStackTrace();
        }

    }


    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){

        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            am.abandonAudioFocus(afChangeListener);
        }
    }
};





    //当失去焦点的时候降低音量，得到焦点的时候增加音量
    AudioManager.OnAudioFocusChangeListener anotherafChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
            // Lower the volume
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            // Raise it back to normal
        }
    }
};











}
