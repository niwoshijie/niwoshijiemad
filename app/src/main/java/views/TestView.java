package views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by LiuShao on 2016/6/28.
 */
public class TestView {

//    DashPathEffect:DashPathEffect是PathEffect类的一个子类,
// 可以使paint画出类似虚线的样子,并且可以任意指定虚实的排列方式. 好了，
// 我们通过canvas.drawPath(path, pathPaint)绘制如下图
    //----绘制虚线---------------------
    public void drawDashPath(){
        Canvas canvas = new Canvas();

        Paint pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(2);
        DashPathEffect mEffects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);
        Path path = new Path();
        path.moveTo(200, 600);
        path.lineTo(800, 600);
        pathPaint.setPathEffect(mEffects);
        canvas.drawPath(path, pathPaint);
    }

    //----绘制矩形------------
    public void drawRect(){
        Canvas canvas = new Canvas();
//      setBackgroundResource(R.drawable.default_bg);//设置背景色
        Paint paint = new Paint();// 定义画笔
        paint.setStyle(Paint.Style.FILL);//设置实心
        paint.setAntiAlias(true);// 消除锯齿
        paint.setColor(Color.WHITE);//设置画笔颜色
        paint.setStrokeWidth(40);// 设置paint的外框宽度
        canvas.drawRect(200, 200, 800, 220, paint);//绘制矩形
        //----绘制圆---------------------
        canvas.drawCircle(350, 350, 100, paint);
        //----绘制圆---------------------

    }



}
