package mainpagers.cpackage.othertest;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import liushaobo.mad.R;
import utils.TextStyleUtils;
import views.textviews.AutoScrollTextView;
import views.textviews.MyScrollTextView;
import views.textviews.MyScrollTextView2;
import views.textviews.MyTextView;
import views.textviews.ScrollTextView;

/**
 * Created by LiuShao on 2016/4/19.
 */

public class TestActivity extends BaseActivity {

    @ViewInject(R.id.tv_test)
    private TextView tv_test;

    @ViewInject(R.id.v_tv_astv)
    private AutoScrollTextView v_tv_astv;

    @ViewInject(R.id.v_tv_stv)
    private ScrollTextView v_tv_stv;

    @ViewInject(R.id.v_tv_mytv)
    private MyTextView v_tv_mytv;

    private Context context;
    private String testString = "设置当文字过长时,该控件该如何显示。有如下值设置：”start”—–省略号显示在开头；”end”——省略号显示在结尾；”middle”—-省略号显示在中间；”marquee” ——以跑马灯的方式显示(动画横向移动)";

    @ViewInject(R.id.c_pager_tt_ll)
    private LinearLayout c_pager_tt_ll;

    private MyScrollTextView scrollTextView;

    @Override
    public void initView() {
        setContentView(R.layout.c_pager_layout_test);
        x.view().inject(this);
    }

    @Override
    public void setListener() {}

    @Override
    protected void initData() {
        context = this;
        tv_test.setText(TextStyleUtils.hightLightString("￥20.36"));

        v_tv_astv.setText(testString);

        v_tv_astv.setDirection(3);
        v_tv_astv.setScrollSpeed(3);

        v_tv_stv.setText(testString);

        v_tv_mytv.setDirection(3);
        v_tv_mytv.setScrollSpeed(3f);
        v_tv_mytv.setText(testString);


        scrollTextView = new MyScrollTextView.Builder()
                .setmScrollSpeed(3)
                .setTextColor(Color.RED)
                .setTextSize(30)
                .setTextString(testString)
                .setmDirection(3)
                .setFontFamily(0)
                .setBackColor(Color.BLACK)
                .setContext(context)
                .build();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
        scrollTextView.setLayoutParams(params);
        c_pager_tt_ll.addView(scrollTextView);

        AbsoluteLayout.LayoutParams layoutParams= new AbsoluteLayout.LayoutParams(1920, 200, 0,600);
        MyScrollTextView2 marquee = new MyScrollTextView2(context);
        marquee.setText("小乔大逗比，小乔大逗比，小乔大逗比，小乔大逗比小乔大逗比小乔大逗比，小乔大逗比，小乔大逗比，小乔大逗比小乔大逗比小乔大逗比，小乔大逗比");
        marquee.setTextSize(300);
        marquee.setTextColor(Color.RED);
        marquee.setBackColor(Color.BLACK);
        marquee.setDirection(2);
        marquee.setScrollSpeed(3);
        marquee.setTextFont(1);
        marquee.setLayoutParams(layoutParams);
        c_pager_tt_ll.addView(marquee);
    }



}
