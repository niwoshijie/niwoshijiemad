package mainpagers.cpackage.selfserviceapp.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by LiuShao on 2016/4/6.
 */
public class ScreenUtils {

    /**
     * 根据手机分辨率从dp转成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从px转dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属�?scaledDensity�?
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @param fontScale（DisplayMetrics类中属�?scaledDensity�?
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 得到屏幕宽度
     * @param context
     * @return
     */
    public static int getWinWidth(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 得到屏幕高度
     * @param context
     * @return
     */
    public static int getWinHight(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }



}
