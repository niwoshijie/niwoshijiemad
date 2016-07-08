package views.textviews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

/*可移动的窗口小控件*/
public class TableShowView extends ImageButton {


    public TableShowView(Context context) {
        this(context,null);
    }

    public TableShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int oldOffsetX ;
    int oldOffsetY ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        final int action = event.getAction();
//        float x = event.getX();
//        float y = event.getY();
//        if (action == MotionEvent.ACTION_DOWN) {
//            lastX = x;
//            lastY = y;
//
//        } else if (action == MotionEvent.ACTION_MOVE) {
//            mWMParams.x += (int) (x - lastX); // 偏移量
//            mWMParams.y += (int) (y - lastY); // 偏移量
//
//            tag = 1;
//            mWM.updateViewLayout(win, mWMParams);
//        } else if (action == MotionEvent.ACTION_UP) {
//            int newOffsetX = mWMParams.x;
//            int newOffsetY = mWMParams.y;
//            if (oldOffsetX == newOffsetX && oldOffsetY == newOffsetY) {
//
//            } else {
//                tag = 0;
//            }
//        }
//        return true;

        return super.onTouchEvent(event);
    }

    // 触屏监听
    float lastX, lastY;



}
