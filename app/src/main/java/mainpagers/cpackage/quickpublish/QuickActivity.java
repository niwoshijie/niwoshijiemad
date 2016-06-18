package mainpagers.cpackage.quickpublish;


import android.app.Activity;
import android.widget.FrameLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import base.APP;
import base.BaseActivity;
import liushaobo.mad.R;
import mainpagers.cpackage.quickpublish.pager.BaseWeiChatPager;
import mainpagers.cpackage.quickpublish.pager.WeiChatAdapter;
import mainpagers.cpackage.quickpublish.pager.WeiChatImageView;
import mainpagers.cpackage.quickpublish.pager.WeiChatShowPager;
import mainpagers.cpackage.quickpublish.pager.WeiChatTextPager;
import mainpagers.cpackage.quickpublish.pager.WeiChatVideoPager;
import mainpagers.cpackage.quickpublish.pager.WeiChatViewRollPager;
import mainpagers.cpackage.quickpublish.pager.WeiChatVoicePager;
import mainpagers.cpackage.quickpublish.pager.WeiTextToSpeechPager;
import views.pagers.LazyViewPager;


public class QuickActivity extends BaseActivity {
    @ViewInject(R.id.fl_wei_chat)
    FrameLayout flWeiChat;

    private List<BaseWeiChatPager> baseWeiChatPagers;//存放播放内容的显示list
    private WeiChatViewRollPager weiChatViewRollPager;
    public static Activity context;


    @Override
    public void initView() {
        setContentView(R.layout.quick_publish_viewpager_test);
        x.view().inject(this);
        context = this;
        baseWeiChatPagers = new ArrayList<>();
        baseWeiChatPagers.add(new WeiChatTextPager(APP.mAppApplication));
        baseWeiChatPagers.add(new WeiChatImageView(APP.mAppApplication));
        baseWeiChatPagers.add(new WeiChatVoicePager(APP.mAppApplication));
        baseWeiChatPagers.add(new WeiChatVideoPager(APP.mAppApplication));
        baseWeiChatPagers.add(new WeiChatShowPager(APP.mAppApplication));
        baseWeiChatPagers.add(new WeiTextToSpeechPager(APP.mAppApplication));
        weiChatViewRollPager = new WeiChatViewRollPager(this);
        WeiChatAdapter weiChatAdapter = new WeiChatAdapter(baseWeiChatPagers);
        weiChatViewRollPager.setAdapter(weiChatAdapter);
        weiChatViewRollPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                baseWeiChatPagers.get(position).initData("");
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        flWeiChat.addView(weiChatViewRollPager);
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void initData() {
    }

}
