package views.dialogshow;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.ybselfservice.AppActivityManager;
/**
 * Created by Administrator on 2015/8/3.
 */
public class PopupWindowHelper {

    private View popupView;
    private PopupWindow mPopupWindow;
    private static final int TYPE_WRAP_CONTENT = 0, TYPE_MATCH_PARENT = 1;

    private int popWidth;
    private int popHeight;

    public PopupWindowHelper(View view, int width, int height) {
        this.popupView = view;
        this.popWidth = width;
        this.popHeight = height;
    }

    public void showAsDropDown(View anchor) {
        mPopupWindow.showAsDropDown(anchor);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        backAlpha(1.0f);
    }

    public void showAsPopUp(View anchor, int xoff, int yoff) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.popupwindow_animation);
        popupView.measure(popWidth, popHeight);
        int height = popupView.getMeasuredHeight();
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupView.setBackgroundDrawable(dw);
        backgroundAlpha(0.5f);//0.0-1.0
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, location[0] + xoff, location[1] - height + yoff);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backAlpha(1.0f);
                if(onDisMisListener!=null){
                    onDisMisListener.Dismissed();
                }
            }
        });
    }

    public interface onDisMisListener{void Dismissed();}
    public onDisMisListener onDisMisListener;
    public void setOnDisMisListener(PopupWindowHelper.onDisMisListener onDisMisListener) {
        this.onDisMisListener = onDisMisListener;
    }

    public void showFromBottom(View anchor) {
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationFromButtom);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
    }

    public void showFromTop(View anchor) {
        initPopupWindow(TYPE_MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationFromTop);
        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, getStatusBarHeight());
    }

    /**
     * touch outside dismiss the popupwindow, default is ture
     * @param isCancelable
     */
    public void setCancelable(boolean isCancelable) {
        if (isCancelable) {
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        } else {
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
        }
    }

    public void initPopupWindow(int type) {
        if (type == TYPE_WRAP_CONTENT) {
            mPopupWindow = new PopupWindow(popupView, popWidth, popHeight);
        } else if (type == TYPE_MATCH_PARENT) {
            mPopupWindow = new PopupWindow(popupView, popWidth, popHeight);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
    }

    private int getStatusBarHeight() {
        return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        Activity activity = AppActivityManager.getAppActivityManager().currentActivity();
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        activity.getWindow().setAttributes(lp);
    }

    public void backAlpha(float bgAlpha) {
        Activity activity = AppActivityManager.getAppActivityManager().currentActivity();
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}
