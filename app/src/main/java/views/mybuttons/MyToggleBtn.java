package views.mybuttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/6/27.
 */
public class MyToggleBtn extends View implements View.OnClickListener {
    private Bitmap bgBitmap;
    private Bitmap slideBitmap;
    private Paint paint;
    private int slideLeftMax;

    /**
     * 在布局文件中声明该控件，调用此方法
     * @param context
     * @param attrs
     */
    public MyToggleBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    /*
     * 初始化
     */
    private void init() {
        
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slideBitmap =BitmapFactory.decodeResource(getResources(),R.mipmap.slide_button);
        //滑动图片，左边界的最大值
        slideLeftMax=bgBitmap.getWidth()-slideBitmap.getWidth();

        paint =new Paint();
        paint.setAntiAlias(true);//抗锯齿

        //添加点击事件
        setOnClickListener(this);
    }
    //判断触摸时，是否发生滑动
    private boolean isSliding =false;
    @Override//相应view的点击事件
    public void onClick(View v) {
        if(isSliding){
            return;
        }
        //切换开关状态
        currState=!currState;
        flushState();
    }

    /**
     * 刷新状态，根据当前的状态，刷新界面
     */
    private void flushState() {
        if(currState){
            //开状态
            slideLeft=slideLeftMax;
        }else {
            //关状态
            slideLeft=0;
        }
        flushView();
    }
    private void flushView() {
        //保证slideLeft>=0同时<=slideLeftMax
        if(slideLeft<0){
            slideLeft=0;
        }if(slideLeft>slideLeftMax){
            slideLeft=slideLeftMax;
        }
        invalidate();//刷新界面

    }
    //当前的开关状态，true为开
    private boolean currState=false;
    private int slideLeft=0;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图
        canvas.drawBitmap(bgBitmap, 0,0, paint);
        //绘制滑动图片
        canvas.drawBitmap(slideBitmap,slideLeft,0, paint);
    }

    @Override//指定view的大小
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当前view的高度，就和背景图的大小一样
        int measuredWidth =bgBitmap.getWidth();
        int measuredHeight=bgBitmap.getHeight();
        //指定测量的高度
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int lastX;//上一个事件的X坐标
    private int  downX;//down事件中的X坐标
    /**
     * 重写该方法，处理触摸事件
     *如果view消费了事件，那么返回true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super注释以后，onclick事件就失效了，因为，点击这个动作，也是从onTouchEvent方法中解析出来，符合一定的要求，就是一个点击事件
        //系统中如果发现view产生了up事件，就认为发生了onclick 动作，就执行listener.onclick方法
        super.onTouchEvent(event);
        //点击切换开关，与触摸滑动切换开关，就会产生冲突，我们自己锁定，如果手指在屏幕上移动
        //，超过15个像素，就按滑动来切换开关，同时禁用点击切换开关的动作
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX=lastX=(int) event.getX();//相对于当前view的坐标
                //event.getRawX();是相对于屏幕的坐标down事件发生时，肯定不是滑动的动作
                isSliding=false;
                break;
            case MotionEvent.ACTION_MOVE:
                //获得距离
                int disX=(int) (event.getX()-lastX);
                //改变滑动图标的左边界
                slideLeft+=disX;
                flushView();
                //重写赋值
                lastX=(int) event.getX();
                //判断是否发生滑动事件
                if(Math.abs(event.getX()-downX)>15){
                    //手指在屏幕上的滑动的距离大于15像素
                    isSliding=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //只有发生了滑动，才执行一下代码
                if(isSliding){
                    //如果slideLeft>最大值的一半，当前时开状态，否则是关的状态
                    if(slideLeft>slideLeftMax/2){
                        //开状态
                        currState=true;
                    }else{
                        currState=false;
                    }
                    flushState();
                }

                break;
        }
        return true;
    }
}
