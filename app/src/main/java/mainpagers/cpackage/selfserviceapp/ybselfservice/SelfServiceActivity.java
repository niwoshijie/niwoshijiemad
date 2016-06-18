package mainpagers.cpackage.selfserviceapp.ybselfservice;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/4/14.
 */

/**
 * 播放广告
 * 计时打样时间
 */
@ContentView(R.layout.activity_main)
public class SelfServiceActivity extends  BaseActivity{
    @ViewInject(R.id.iv_finger_animation)
    private ImageView iv_finger_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFingerAnimation();
    }

    @Event(R.id.ll_self_ll)
    private void jumpToNext(View view){
        startNextActivity(SelfServiceActivity.this, DetialActivity.class);
    }

    /**
     * 开启手指动画
     */
    private void startFingerAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        iv_finger_animation.clearAnimation();
        iv_finger_animation.startAnimation(animation);
    }




}
