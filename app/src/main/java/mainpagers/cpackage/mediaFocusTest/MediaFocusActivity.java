package mainpagers.cpackage.mediaFocusTest;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import base.BaseActivity;
import base.MADConstant;
import butterknife.ButterKnife;
import butterknife.InjectView;
import liushaobo.mad.R;
import utils.TextToSpeechAll;
import views.dialogshow.PopupWindowHelper;

/**
 * Created by LiuShao on 2016/2/18.
 */
public class MediaFocusActivity extends BaseActivity {

    @InjectView(R.id.show_pop)
    Button show_pop;

    @InjectView(R.id.media_foucs_play)
    Button mediaFoucsPlay;
    @InjectView(R.id.text_to_pasue_media)
    Button text_to_pasue_media;

    @InjectView(R.id.text_foucs_play)
    Button text_foucs_play;

    @InjectView(R.id.text_to_stop_media)
    Button text_to_stop_media;
    @InjectView(R.id.btn_play_video)
    Button btn_play_video;

    @InjectView(R.id.reTry)
    Button reTry;

    @InjectView(R.id.c_pager_media_video_view)
     VideoView c_pager_media_video_view;

    @InjectView(R.id.btn_set_Equalize)
    Button btn_set_Equalize;

    @InjectView(R.id.btn_stopvideo)
    Button btn_stopvideo;

    @InjectView(R.id.btn_test_sleep)
    Button btn_test_sleep;

    private static final String TAG = "MediaFocusActivity";
    private Activity context;

    @Override
    public void initView() {
        setContentView(R.layout.meida_foucus_test);
        ButterKnife.inject(this);
        context = this;
    }

    @Override
    public void initValue() {}

    @Override
    public void initData() {
        audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        initBtn();
    }

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private MediaFoucsChangeListener mediaFoucsChangeListener;

    private void initBtn() {
        mediaFoucsChangeListener = new MediaFoucsChangeListener();

        mediaFoucsPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              playVoice();
                playMusic();
            }
        });

        text_foucs_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextToSpeechAll.getTextToSpeechAll().initVoice();
                TextToSpeechAll.getTextToSpeechAll().speakVoice("this is a test to show audio");
            }
        });

        text_to_pasue_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        text_to_stop_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        reTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay();
            }
        });

        btn_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideoview();
            }
        });


        btn_set_Equalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyEqualize();
            }
        });
        btn_stopvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVideoview();
            }
        });
        btn_test_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToff("echo on>/sys/power/state",60);
                setToff("echo mem>/sys/power/state", 10);
            }
        });

        show_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
    }

    private PopupWindowHelper popupWindowHelper;
    private void showPop() {
        View view = LayoutInflater.from(context).inflate(R.layout.right_view_item, null);
        popupWindowHelper = new PopupWindowHelper(view, 350, 250);
        popupWindowHelper.showAsPopUp(show_pop, 0, 69);


    }

    private void setToff(String deviceString,int time) {
        TimerInstance timerInstance = new TimerInstance();
        timerInstance.setTime(time);
        timerInstance.setDeviceString(deviceString);
        timerInstance.startTimer();
    }

    private Equalizer mEqualizer;

    /**
     * 设置均衡器
     */
    private void setMyEqualize() {
        mEqualizer = new Equalizer(0,mediaPlayer.getAudioSessionId());
        mEqualizer.setEnabled(true);
        final short minEQLevel = mEqualizer.getBandLevelRange()[0];
        short brands = mEqualizer.getNumberOfBands();
        for (short i = 0; i < brands; i++) {
            if(i == brands-1){
                mEqualizer.setBandLevel(i,(short) (10+minEQLevel));
            }
        }
    }

    private void playVoice() {
        mediaPlayer = MediaPlayer.create(context, R.raw.pop_danthology);
        audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        audioManager.requestAudioFocus(mediaFoucsChangeListener
                , AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT);
        // 设置音频流的类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 通过异步的方式装载媒体资源
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕开始播放流媒体
                mediaPlayer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    /**
     * 点击播放音乐
     */
    private void playMusic() {

        mediaPlayer = MediaPlayer.create(context, R.raw.pop_danthology);

//        AssetFileDescriptor fileDescriptor = null;
//        try {
//            fileDescriptor = getAssets().openFd("pop_danthology.mp3");
//            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
//                    fileDescriptor.getLength());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        MediaInterface.getMediaFocusInterface().setMediaFocusInterface(new MediaInterface.MediaFocusInterface() {
            @Override
            public void onReceiveAudioStart(int type) {
                Log.e(TAG, "MediaInterface.getMediaFocusInterface().onReceiveAudioStart.onReceiveAudioStart");
                if (type != MediaInterface.MEDIA_MUSIC) {
                    pause();
                }
            }

            @Override
            public void onReceiveAudioStop(int type) {
                Log.e(TAG, "MediaInterface.getMediaFocusInterface()--------------------" + type);
                if (type != MediaInterface.MEDIA_MUSIC) {
                    begin();
                }
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕开始播放流媒体
                mediaPlayer.start();
                mediaFoucsPlay.setFocusable(false);

                if (MediaInterface.getMediaFocusInterface().mediaFocusInterface != null) {
                    MediaInterface.getMediaFocusInterface().mediaFocusInterface.onReceiveAudioStart(MediaInterface.MEDIA_MUSIC);
                    Log.e(TAG, "MediaInterface.getMediaFocusInterface().onReceiveAudioStop.MEDIA_MUSIC");
                }
            }
        });
    }

    /**
     * 暂停
     */
    protected void begin() {
        mediaPlayer.start();
    }
    /**
     * 暂停
     */
    protected void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        mediaFoucsPlay.setFocusable(true);
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

    private static final String videoUri = MADConstant.MAD_PATH + "AAA.mp4";

    /**
     * 播放视频
     */
    protected void playVideoview() {
        c_pager_media_video_view.setBackgroundColor(Color.parseColor("#00ffffff"));
        c_pager_media_video_view.setVideoPath(videoUri);

        c_pager_media_video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                c_pager_media_video_view.requestFocus();
                c_pager_media_video_view.requestFocusFromTouch();
                c_pager_media_video_view.start();

                if(MediaInterface.getMediaFocusInterface().mediaFocusInterface!=null){
                    Log.e(TAG,"视频开始播放");
                    MediaInterface.getMediaFocusInterface().mediaFocusInterface.onReceiveAudioStart(MediaInterface.MEDIA_VIDEO);
                }
            }
        });

        c_pager_media_video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    Toast.makeText(context, "视频服务异常",
                            Toast.LENGTH_SHORT).show();
                } else if (what == MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK) {
                }
                return true;
            }
        });

        c_pager_media_video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                c_pager_media_video_view.setBackgroundColor(Color.parseColor("#000000"));
            }
        });

        MediaInterface.getMediaFocusInterface().setMediaFocusInterface(new MediaInterface.MediaFocusInterface() {
            @Override
            public void onReceiveAudioStart(int type) {
                Log.e(TAG, "视频接收到的" + type);
                if (type != MediaInterface.MEDIA_VIDEO) {
                    pausevideo();
                }
            }
            @Override
            public void onReceiveAudioStop(int type) {
                Log.e(TAG, "视频接收到的--------------------" + type);
                if (type != MediaInterface.MEDIA_VIDEO) {
                    beginVideo();
                }
            }
        });
    }


    private void stopVideoview() {
        c_pager_media_video_view.stopPlayback();
        c_pager_media_video_view.setBackgroundColor(Color.parseColor("#000000"));
    }

    private void beginVideo() {
        if (!c_pager_media_video_view.isPlaying()) {
            c_pager_media_video_view.start();
        }
    }

    private void pausevideo() {
        if (c_pager_media_video_view.isPlaying()) {
            c_pager_media_video_view.pause();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}





