package base;

import liushaobo.mad.R;
import mainpagers.cpackage.appinformations.AppInforMation;
import mainpagers.cpackage.drawerpackage.DrawerActivity;
import mainpagers.cpackage.eventbustest.EventBusActivity;
import mainpagers.cpackage.hardware.HardWareDemo;
import mainpagers.cpackage.mediaFocusTest.AudioFxDemo;
import mainpagers.cpackage.mediaFocusTest.MediaFocusActivity;
import mainpagers.cpackage.othertest.TestActivity;
import mainpagers.cpackage.pulllayout.PullLayoutActivity;
import mainpagers.cpackage.pullrefresh.PullRefreshActivity;
import mainpagers.cpackage.quickpublish.QuickActivity;
import mainpagers.cpackage.selfserviceapp.ybselfservice.SelfServiceActivity;
import mainpagers.cpackage.serverScoket.ServerScoketActivity;
import mainpagers.cpackage.stytlespackage.StytleActivity;
import mainpagers.cpackage.t23DesignMode.T23DesignMode;
import mainpagers.cpackage.thriddeffect.TouchRotateActivity;
import mainpagers.cpackage.userdefinepagerindicator.MyPageIndicator;

/**
 * Created by LiuShao on 2016/1/26.
 */
public interface ContantValue {
    // ---------------------------C页面------------------------------------

    String[] itemName = {"音频案例", "均衡器", "控件案例", "系统通信", "3D旋转",
            "一些样式案列", "手机内部信息", "快捷直发", "自定义导航条","上拉特效",
    "自助机","测试","23种设计模式","eventbus","硬件调用测试","下拉刷新"};
    int[] imageList = {R.mipmap.g1, R.mipmap.g2, R.mipmap.g3,
            R.mipmap.g4, R.mipmap.g5, R.mipmap.g6,
            R.mipmap.g7, R.mipmap.g8, R.mipmap.g9
    ,R.mipmap.g10,R.mipmap.g11,R.mipmap.g12,R.mipmap.g13,R.mipmap.g14,R.mipmap.g15,R.mipmap.g16};
    Class[] objects = {
            MediaFocusActivity.class,
            AudioFxDemo.class,
            DrawerActivity.class,
            ServerScoketActivity.class,
            TouchRotateActivity.class,
            StytleActivity.class,
            AppInforMation.class,
            QuickActivity.class,
            MyPageIndicator.class,
            PullLayoutActivity.class,
            SelfServiceActivity.class,
            TestActivity.class,
            T23DesignMode.class,
            EventBusActivity.class,
            HardWareDemo.class,
            PullRefreshActivity.class
    };

}
