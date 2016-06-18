package mainpagers.cpackage.othertest;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/4/19.
 */

public class TestActivity extends BaseActivity {

    @ViewInject(R.id.tv_test)
    private TextView tv_test;

    @Override
    public void initView() {
        setContentView(R.layout.c_pager_layout_test);
        x.view().inject(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void initData() {
        tv_test.setText(highlight("￥20.36"));
    }



    /**
     * 关键字高亮显示
     * @param
     */
    public SpannableString highlight(String message){

        SpannableString spannable = new SpannableString(message);
        CharacterStyle span = null;
        if(message.contains(".")){
            int poitionLocation = message.indexOf(".");
            span = new ForegroundColorSpan(Color.RED);//需要标红
//          spannable.setSpan(span, m.start(),  m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(2.0f), 0,poitionLocation, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannable;
        }else{
            return null;
        }
    }











}
