package views.textviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import mainpagers.cpackage.quickpublish.pager.BaseWeiChatPager;

public class ScrollTextView extends TextView {

    private int mWholeLen = 0;
    private Paint mPaint = null;
    private final ScrollTextView.TextMoveHandler moveHandler = new ScrollTextView.TextMoveHandler(this);
    private float step = 0.0F;
    private String text = "";
    private List<String> textList = new ArrayList();
    private float width = 0.0F;

    public ScrollTextView(Context context) {
        super(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void update() {
        this.mPaint = this.getPaint();
        this.mPaint.setColor(this.getCurrentTextColor());
        this.text = this.getText().toString();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void removeHandler(){
        if(this.moveHandler != null) {
            this.moveHandler.removeCallbacksAndMessages(null);
        }
        step =  0.0F;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(this.moveHandler != null) {
            this.moveHandler.removeCallbacksAndMessages(null);
        }
    }

    public void onDraw(Canvas canvas) {

        if(getTextViewHeight()>this.getHeight()){
            BaseWeiChatPager.weiChatPagerReceive.stop();
            if(this.moveHandler != null) {
                this.moveHandler.sendMessage(this.moveHandler.obtainMessage(0));
            }

            if(this.textList.size() != 0) {
                for(int i = 0; i  < this.textList.size(); ++i ) {
                    canvas.drawText((String)this.textList.get(i), 0.0F, (float)this.getHeight() + (float)(i  + 1) * this.mPaint.getTextSize() - this.step, this.mPaint);
                }
                this.step += 1.5F;
                if(this.step >= (float)this.getHeight()/2 + (float)this.textList.size() * this.mPaint.getTextSize()) {
                    this.step = 0.0F;
                    BaseWeiChatPager.weiChatPagerReceive.start();
                    return;
                }
            }
        }else{
            super.onDraw(canvas);
        }
    }

    private int getTextViewHeight() {
        Layout layout = this.getLayout();
        int desired = layout.getLineTop(this.getLineCount());
        int padding = this.getCompoundPaddingTop() + this.getCompoundPaddingBottom();
        return desired + padding;
    }


    protected void onMeasure(int context, int attrs) {
        super.onMeasure(context, attrs);
        this.update();
        this.width = (float) MeasureSpec.getSize(context) - 25;
        if(MeasureSpec.getMode(context) != MeasureSpec.EXACTLY) {
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

                Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
                this.y_coordinate = (float)(MeasureSpec.getSize(attrs) / 2 - (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.top);
            }
        }
    }

    private float y_coordinate = 0.0F;

    protected void onVisibilityChanged(View context, int attrs) {
        super.onVisibilityChanged(context, attrs);
        if(attrs != INVISIBLE && attrs != GONE) {
            if(attrs == VISIBLE) {

                if(this.moveHandler != null) {
                    this.moveHandler.sendMessage(this.moveHandler.obtainMessage(0));
                    return;
                }
            }
        } else {

            if(this.moveHandler != null) {
                this.moveHandler.removeCallbacksAndMessages((Object)null);
            }
        }
    }


    private static class TextMoveHandler extends Handler {
        private final WeakReference<ScrollTextView> mScroll;

        public TextMoveHandler(ScrollTextView context) {
            this.mScroll = new WeakReference(context);
        }

        public void handleMessage(Message context) {
            switch(context.what) {
                case 0:
                    ScrollTextView attrs = (ScrollTextView)this.mScroll.get();
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



    private class TextTouchListener implements OnTouchListener {
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


                    this.startPoint = new PointF(event.getX(), event.getY());
                    this.start_step = ScrollTextView.this.step;
                    this.mMode = 1;
                    return true;
                case 1:


                    this.startPoint = null;
                    this.start_step = 0.0F;
                    this.mMode = 0;
                    return true;
                case 2:


                    if(this.mMode == 1) {
                        int direction = 0;
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

                        ScrollTextView.this.step = i + this.start_step;
                        ScrollTextView.this.invalidate();
                        return true;
                    }
                default:
                    return true;
            }
        }
    }




}
