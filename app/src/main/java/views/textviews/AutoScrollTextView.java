package views.textviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuShao on 2016/1/26.
 */
public class AutoScrollTextView extends TextView{


    private static final Boolean Debug = Boolean.valueOf(false);

    private static final String TAG = AutoScrollTextView.class.getSimpleName();
    private int mDirection = 3;//向下滚动0,向左滚动3,向右滚动2,向上滚动1
    private int mScrollSpeed = 1; //2高速,0低速,1中速,3更高速，4更更高速

    private int mWholeLen = 0;
    private Paint mPaint = null;
    private final AutoScrollTextView.TextMoveHandler moveHandler = new AutoScrollTextView.TextMoveHandler(this);
    private float step = 0.0F;
    private String text = "";
    private List<String> textList = new ArrayList();
    private float width = 0.0F;
    private float y_coordinate = 0.0F;

    public AutoScrollTextView(Context context) {
        super(context);

        this.init((AttributeSet)null, 0);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs, 0);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs, defStyleAttr);

    }

    private void init(AttributeSet paramAttributeSet, int attrs) {
        this.mDirection = 0;
        this.mScrollSpeed = 1;
        this.setOnTouchListener(new AutoScrollTextView.TextTouchListener());
    }

    private void update() {
        this.mPaint = this.getPaint();
        this.mPaint.setColor(this.getCurrentTextColor());
        this.text = this.getText().toString();

    }

    /**
     * 设置滚动速度
     * @return
     */
    public void setScrollSpeed(float scrollSpeed) {
        if(scrollSpeed == 0.2){
            this.mScrollSpeed = 0;
        }else if(scrollSpeed == 0.5){
            this.mScrollSpeed = 0;
        }else if(scrollSpeed == 1){
            this.mScrollSpeed = 1;
        }else if(scrollSpeed == 2){
            this.mScrollSpeed = 2;
        }else if(scrollSpeed == 3){
            this.mScrollSpeed = 2;
        }else{
            this.mScrollSpeed = 1;
        }
    }

    /**
     * 设置滚动方向
     * @param direction
     */
    public void setDirection(int direction){
        this.mDirection = direction;
    }


    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(Debug.booleanValue()) {
            Log.d(TAG, "onAttachedToWindow");
        }
        if(this.moveHandler != null) {
            this.moveHandler.sendMessage(this.moveHandler.obtainMessage(0));
        }

    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(Debug.booleanValue()) {
            Log.d(TAG, "onDetachedFromWindow");
        }

        if(this.moveHandler != null) {
            this.moveHandler.removeCallbacksAndMessages((Object) null);
        }
    }

    public void onDraw(Canvas canvas) {
        if(this.textList.size() != 0) {
            if(this.mDirection == 0) {
                for(int i = 0; i  < this.textList.size(); ++i ) {
                    canvas.drawText((String)this.textList.get(i), 0.0F, (float)this.getHeight() + (float)(i  + 1) * this.mPaint.getTextSize() - this.step, this.mPaint);
                }

                setSpeed(this.mScrollSpeed);

                if(this.step >= (float)this.getHeight() + (float)this.textList.size() * this.mPaint.getTextSize()) {
                    this.step = 0.0F;
                    return;
                }
            } else if(this.mDirection == 1) {
                for(int i = 0; i < this.textList.size(); ++i) {
                    canvas.drawText((String)this.textList.get(i), 0.0F, (float)(-(this.textList.size() - i)) * this.mPaint.getTextSize() + this.step, this.mPaint);
                }

                setSpeed(this.mScrollSpeed);

                if(this.step >= (float)this.getHeight() + (float)this.textList.size() * this.mPaint.getTextSize()) {
                    this.step = 0.0F;
                    return;
                }
            } else {
                if(this.mDirection == 2) {
                    if(this.step >= (float)(this.getWidth() + this.mWholeLen)) {
                        this.step = 0.0F;
                    }

                    setSpeed(this.mScrollSpeed);

                    canvas.drawText(this.text, (float)(-this.mWholeLen) + this.step, this.y_coordinate, this.mPaint);
                    return;
                }

                if(this.mDirection == 3) {
                    if(this.step >= (float)(this.getWidth() + this.mWholeLen)) {
                        this.step = 0.0F;
                    }

                    setSpeed(this.mScrollSpeed);

                    canvas.drawText(this.text, (float)this.getWidth() - this.step, this.y_coordinate, this.mPaint);
                    return;
                }
            }
        }
    }


    private void setSpeed(int mScrollSpeed){
        switch(mScrollSpeed){
            case 0:
                this.step += 0.5F;
                break;
            case 1:
                ++this.step;
                break;
            case 2:
                this.step += 1.5F;
                break;
            case 3:
                this.step += 2.0F;
                break;
            case 4:
                this.step += 2.5F;
        }
    }

    protected void onMeasure(int context, int attrs) {
        super.onMeasure(context, attrs);
        if(Debug.booleanValue()) {
            Log.d(TAG, "onMeasure");
        }

        this.update();
        this.width = (float) View.MeasureSpec.getSize(context) - 25;
        if(View.MeasureSpec.getMode(context) != View.MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollLayout only can mCurScreen run at EXACTLY mode!");
        } else {
            float width = 0.0F;
            if(this.text != null && this.text.length() != 0) {
                this.textList.clear();
                StringBuilder stringbuilder = new StringBuilder();
                for(int i = 0; i < this.text.length(); ++i) {
                    char textValue = this.text.charAt(i);
                    float onlywidth = this.mPaint.measureText(this.text.substring(i, i + 1));
                    width += onlywidth;

                    if(width < this.width) {
                        stringbuilder.append(textValue);
                        if (textValue == 10) {//判断是否换行
                            this.textList.add(stringbuilder.toString());
                            stringbuilder.delete(0, -1 + stringbuilder.length());
                            width = 0;
                        } else {
                            if(i == -1 + this.text.length()) {
                                this.textList.add(stringbuilder.toString());
                            }
                        }
                    } else {
                        this.textList.add(stringbuilder.toString().substring(0, -1 + stringbuilder.toString().length()));
                        stringbuilder.delete(0, -1 + stringbuilder.length());
                        width = 0;
                        --i;
                    }
                }

                this.mWholeLen = 0;

                for(int k = 0; k < this.text.length(); ++k) {
                    this.mWholeLen = (int)((float)this.mWholeLen + this.mPaint.measureText(this.text.substring(k, k + 1)));
                }

                if(Debug.booleanValue()) {
                    Log.d(TAG, "mWholeLen" + this.mWholeLen);
                }

                Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
                this.y_coordinate = (float)(View.MeasureSpec.getSize(attrs) / 2 - (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.top);
                if(Debug.booleanValue()) {
                    Log.d(TAG, "y_coordinate:\t" + this.y_coordinate + "\n" + "height:\t" + this.getHeight() + "\n" + "width:\t" + this.getWidth() + "\n" + "measureWidth:\t" + View.MeasureSpec.getSize(context) + "\n" + "measureHeight:\t" + View.MeasureSpec.getSize(attrs) + "\n");
                    return;
                }
            }

        }
    }

    protected void onVisibilityChanged(View context, int attrs) {
        super.onVisibilityChanged(context, attrs);
        if(attrs != INVISIBLE && attrs != GONE) {
            if(attrs == VISIBLE) {
                if(Debug.booleanValue()) {
                    Log.d(TAG, "onVisibilityChanged:\tVISIBLE");
                }

                if(this.moveHandler != null) {
                    this.moveHandler.sendMessage(this.moveHandler.obtainMessage(0));
                    return;
                }
            }
        } else {
            if(Debug.booleanValue()) {
                Log.d(TAG, "onVisibilityChanged:\tINVISIBLE/GONE");
            }

            if(this.moveHandler != null) {
                this.moveHandler.removeCallbacksAndMessages((Object)null);
            }
        }
    }


    private static class TextMoveHandler extends Handler {
        private final WeakReference<AutoScrollTextView> mScroll;

        public TextMoveHandler(AutoScrollTextView context) {
            this.mScroll = new WeakReference(context);
        }

        public void handleMessage(Message context) {
            switch(context.what) {
                case 0:
                    AutoScrollTextView attrs = (AutoScrollTextView)this.mScroll.get();
                    if(attrs != null) {
                        attrs.invalidate();
                    }

                    this.sendMessageDelayed(this.obtainMessage(0), 33L);
                    return;
                default:
                    super.handleMessage(context);
            }
        }
    }

    private class TextTouchListener implements View.OnTouchListener {
        private static final int DRAG_MODE = 1;
        private static final int NONE_MODE = 0;
        private int mMode;
        private PointF startPoint;
        private float start_step;

        private TextTouchListener() {
            this.startPoint = null;
            this.start_step = 0.0F;
            this.mMode = 0;
        }

        public boolean onTouch(View context, MotionEvent event) {
            switch(255 & event.getAction()) {
                case 0:
                    if(AutoScrollTextView.Debug.booleanValue()) {
                        Log.d(AutoScrollTextView.TAG, "ACTION_DOWN");
                    }

                    this.startPoint = new PointF(event.getX(), event.getY());
                    this.start_step = AutoScrollTextView.this.step;
                    this.mMode = 1;
                    return true;
                case 1:
                    if(AutoScrollTextView.Debug.booleanValue()) {
                        Log.d(AutoScrollTextView.TAG, "ACTION_UP");
                    }

                    this.startPoint = null;
                    this.start_step = 0.0F;
                    this.mMode = 0;
                    return true;
                case 2:
                    if(AutoScrollTextView.Debug.booleanValue()) {
                        Log.d(AutoScrollTextView.TAG, "ACTION_MOVE");
                    }

                    if(this.mMode == 1) {
                        int direction = AutoScrollTextView.this.mDirection;
                        float i = 0.0F;
                        switch(direction) {
                            case 0:
                                i = this.startPoint.y - event.getY();
                                break;
                            case 1:
                                i = event.getY() - this.startPoint.y;
                                break;
                            case 2:
                                i = event.getX() - this.startPoint.x;
                                break;
                            case 3:
                                i = this.startPoint.x - event.getX();
                        }

                        AutoScrollTextView.this.step = i + this.start_step;
                        AutoScrollTextView.this.invalidate();
                        return true;
                    }
                default:
                    return true;
            }
        }
    }
}
