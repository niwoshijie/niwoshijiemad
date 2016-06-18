package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.view.View;

public abstract class BaseWeiChatPager {
    private View view;
    public Context context;
    public boolean is_load  =  false;//设置一个标记，表明数据是否已经获取，如果已经获取，那么就不用再次联网获取

    public abstract View initView();//初始化界面用，子类可直接继承

    public View getRootView(){
        return view;//拿到根view
    }

    public abstract void initData(String msg);

    public BaseWeiChatPager(Context context){
        this.context = context;
        view = initView();//拿到实例化的view
    }

    public interface WeiChatPagerReceive{
     void start();
     void stop();
    }

    public static void setWeiChatPagerReceive(WeiChatPagerReceive weiChatPagerReceive) {
        BaseWeiChatPager.weiChatPagerReceive = weiChatPagerReceive;
    }
    public static  WeiChatPagerReceive weiChatPagerReceive;


}
