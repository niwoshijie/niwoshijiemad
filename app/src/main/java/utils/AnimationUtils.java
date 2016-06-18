package utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by LiuShao on 2016/3/18.
 */
public class AnimationUtils {


    /**
     * 翻转动画
     * @param view
     * @param screenWidth
     * @param screenHeight
     * @param SOHWTIME
     */
    private void initAnima(View view,int screenWidth,int screenHeight,int SOHWTIME) {
        ObjectAnimator a = ObjectAnimator.ofFloat(view,
                "rotationY", 180, 360);
        // 动画坐标:屏幕中心点开始,view坐标:屏幕左上角开始,坐标点是view左上角
        ObjectAnimator b = ObjectAnimator.ofFloat(view,
                "translationX", (view.getX() - (screenWidth / 2)) + (view.getWidth() / 2),
                0);
        ObjectAnimator b2 = ObjectAnimator.ofFloat(view,
                "translationY",
                (view.getY() - (screenHeight / 2)) + (view.getHeight() / 2), 0);

        ObjectAnimator c = ObjectAnimator.ofFloat(view, "alpha", 0,
                1);
        ObjectAnimator d = ObjectAnimator.ofFloat(view, "scaleX",
                0.1f, 0.5f, 1);
        ObjectAnimator e = ObjectAnimator.ofFloat(view, "scaleY",
                0.05f, 0.5f, 1);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(SOHWTIME);
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(a, b, b2, c, d, e);
        set.start();
    }


    public void outAnima(View view,int screenWidth,int screenHeight,int SOHWTIME,int viewX,int viewY) {
        // "scaleX", 1, 1.5f, 2: 放大
        // if (UpdateViewUtil.isDialogOut) {
        // UpdateViewUtil.isDialogOut = false;
            ObjectAnimator a = ObjectAnimator.ofFloat(view,
                    "rotationY", 360, 180);
            // 动画坐标:屏幕中心点开始,view坐标:屏幕左上角开始,坐标点是view左上角
            ObjectAnimator b = ObjectAnimator.ofFloat(view,
                    "translationX", 0, (viewX - (screenWidth / 2))+ (view.getX() / 2));
            ObjectAnimator b2 = ObjectAnimator.ofFloat(view,
                    "translationY", 0, (viewY - (screenHeight / 2))
                            + (view.getY() / 2));

            ObjectAnimator c = ObjectAnimator.ofFloat(view,
                    "alpha", 1, 0);
            ObjectAnimator d = ObjectAnimator.ofFloat(view,
                    "scaleX", 1, 0.5f, 0.1f);
            ObjectAnimator e = ObjectAnimator.ofFloat(view,
                    "scaleY", 1, 0.5f, 0.05f);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(SOHWTIME);
            set.setInterpolator(new LinearInterpolator());
            set.playTogether(a, b, b2, c, d, e);
            set.start();

    }



}
