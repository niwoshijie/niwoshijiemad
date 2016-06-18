package mainpagers.cpackage.drawerpackage.circle2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/3/21.
 */
public class BallView extends View {

    public float currentX =30;
    public float currentY = 50;
    private Paint ballPaint = new Paint();

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ballPaint.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 35, ballPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();
        return super.onTouchEvent(event);
    }


}
