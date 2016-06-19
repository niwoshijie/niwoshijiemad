package mainpagers.cpackage.othertest;

import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import liushaobo.mad.R;
import utils.TextStyleUtils;
import views.textviews.AutoScrollTextView;
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

    private String testString = "设置当文字过长时,该控件该如何显示。有如下值设置：”start”—–省略号显示在开头；”end”——省略号显示在结尾；”middle”—-省略号显示在中间；”marquee” ——以跑马灯的方式显示(动画横向移动)";

    @Override
    public void initView() {
        setContentView(R.layout.c_pager_layout_test);
        x.view().inject(this);
    }

    @Override
    public void setListener() {}

    @Override
    protected void initData() {
        tv_test.setText(TextStyleUtils.hightLightString("￥20.36"));

        v_tv_astv.setText(testString);

        v_tv_astv.setDirection(3);
        v_tv_astv.setScrollSpeed(3);

        v_tv_stv.setText(testString);

        v_tv_mytv.setDirection(3);
        v_tv_mytv.setScrollSpeed(3f);
        v_tv_mytv.setText(testString);
    }





}
