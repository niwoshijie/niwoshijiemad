package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import org.xutils.view.annotation.ViewInject;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/1/26.
 */
public class WeiChatShowPager extends BaseWeiChatPager {

    @ViewInject(R.id.weichat_show_image_1)
    private ImageView weichat_show_image_1;
    @ViewInject(R.id.weichat_show_image_2)
    private ImageView weichat_show_image_2;
    @ViewInject(R.id.weichat_show_image_3)
    private ImageView weichat_show_image_3;
    @ViewInject(R.id.weichat_show_image_4)
    private ImageView weichat_show_image_4;
    @ViewInject(R.id.weichat_show_image_5)
    private ImageView weichat_show_image_5;
    @ViewInject(R.id.weichat_show_image_6)
    private ImageView weichat_show_image_6;
    @ViewInject(R.id.weichat_show_image_7)
    private ImageView weichat_show_image_7;
    @ViewInject(R.id.weichat_show_image_8)
    private ImageView weichat_show_image_8;
    @ViewInject(R.id.weichat_show_image_9)
    private ImageView weichat_show_image_9;
    @ViewInject(R.id.weichat_show_image_0)
    private ImageView weichat_show_image_0;

    public WeiChatShowPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.weichat_main_show_aa, null);
        org.xutils.x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData(String msg) {
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.a2);
        weichat_show_image_1.setAnimation(animation);
        setAnimation1(weichat_show_image_2);
        setAnimation2(weichat_show_image_3);
        setAnimation3(weichat_show_image_4);
        setAnimation4(weichat_show_image_5);
        showPagerHandler.sendEmptyMessageDelayed(show_next_position, 3000);
    }

    private ImageView[] views = new ImageView[]{
            weichat_show_image_1,
            weichat_show_image_2,
            weichat_show_image_3,
            weichat_show_image_4,
            weichat_show_image_5,
            weichat_show_image_6,
            weichat_show_image_7,
            weichat_show_image_8,
            weichat_show_image_9,
            weichat_show_image_0
    };

    private int[] ids = new int[]{
            R.anim.alpha_in,R.anim.slide_in_left, R.anim.a2,
            R.anim.fade_back,R.anim.slide_right_in,R.anim.fade_forward,R.anim.alpha_animation1
    };

    private void setImageAnimation(ImageView aaa){
//        Animation ani = new AlphaAnimation(0f, 1f);
//        ani.setDuration(1500);
//        ani.setRepeatMode(Animation.REVERSE);
//        ani.setRepeatCount(Animation.INFINITE);
//        imaegView.startAnimation(ani);
         int  index=(int)(Math.random()*ids.length);
        int rand = ids[index];
        Animation animation = AnimationUtils.loadAnimation(context,rand);

//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.a2);
        aaa.setAnimation(animation);
    }

    private int mCurrentPosition = 0;
    private final int show_next_position = 12345678;

    private Handler showPagerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case show_next_position:
                    mCurrentPosition = (mCurrentPosition+1)%10;
                    setImageAnimation(views[mCurrentPosition]);
                    showPagerHandler.sendEmptyMessageDelayed(show_next_position,2000);
                    break;
            }
        }
    };


    private void setAnimation1(View view){
        LinearInterpolator linearInterpolator2 = new LinearInterpolator();
        Animation animation5_1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0);
        animation5_1.setDuration(25000);
        animation5_1.setInterpolator(linearInterpolator2);
        view.clearAnimation();
    }


    private void setAnimation2(View view){
        LinearInterpolator linearInterpolator2 = new LinearInterpolator();
        Animation animation5_2 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0);
        animation5_2.setDuration(35000);
        animation5_2.setInterpolator(linearInterpolator2);
        view.clearAnimation();
        view.startAnimation(animation5_2);
    }

    private void setAnimation3(View view){
        Animation animation5_3 = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                1.0f);
        animation5_3.setRepeatCount(-1);
        animation5_3.setDuration(500);
        animation5_3.setRepeatMode(Animation.REVERSE);
        view.clearAnimation();
        view.startAnimation(animation5_3);
    }

    private void setAnimation4(View view){
        Animation animation5_4 = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation5_4.setRepeatCount(-1);
        animation5_4.setDuration(500);
        animation5_4.setRepeatMode(Animation.REVERSE);
        view.clearAnimation();
        view.startAnimation(animation5_4);
    }












}
