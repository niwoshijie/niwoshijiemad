package mainpagers.cpackage.eventbustest;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/6/7.
 */
@ContentView(R.layout.c_pager_event_bus)
public class EventBusActivity extends BaseActivity {

    @ViewInject(R.id.tv_event_bus_test)
    private TextView tv_event_bus_test;

    @Override
    public void initView() {
        x.view().inject(this);
    }

    @Override
    public void setListener() {
        EventBus.getDefault().register(this);//该activity被注册成一个订阅者
    }

    @Override
    protected void initData() {
    }

    //有以下四个ThreadMode：
    //PostThread：事件的处理在和事件的发送在相同的进程，所以事件处理时间不应太长，不然影响事件的发送线程，而这个线程可能是UI线程。对应的函数名是onEvent。
    //MainThread: 事件的处理会在UI线程中执行。事件处理时间不能太长，这个不用说的，长了会ANR的，对应的函数名是onEventMainThread。
    //BackgroundThread：事件的处理会在一个后台线程中执行，对应的函数名是onEventBackgroundThread，虽然名字是BackgroundThread，事件处理是在后台线程，但事件处理时间还是不应该太长，因为如果发送事件的线程是后台线程，会直接执行事件，如果当前线程是UI线程，事件会被加到一个队列中，由一个线程依次处理这些事件，如果某个事件处理时间太长，会阻塞后面的事件的派发或处理。
    //Async：事件处理会在单独的线程中执行，主要用于在后台线程中执行耗时操作，每个事件会开启一个线程（有线程池），但最好限制线程的数目。

    //接收事件的回调

    public void onEvent(MyEvent event) {
        Log.e("What", "[onEvent]My Thread is " + Thread.currentThread().getName());
    }

    @Event(value = {R.id.btn_event_}, type = View.OnClickListener.class)
    private void OnClickEvent(View view) {
        EventBus.getDefault().post(new MyEvent());//事件的处理在和事件的发送在相同的进程，
    }

    public void onEventMainThread(MyEvent event) {
        Log.e("What", "[onEventMainThreadMy] Thread is " + Thread.currentThread().getName());
        tv_event_bus_test.setText(Thread.currentThread().getName());
    }

    //事件1接收者：在主线程接收
    public void onEvent(String event) {
//        mShowInfo1.setText(event);
    }

    //事件2接收者：在主线程接收自定义MsgBean消息
    public void onEvent(MsgBean event) {
//        mShowInfo21.setText(event.getMsg());
    }

    //事件3接收者：在主线程接收
    public void onEventMainThread(Integer event) {
//        mShowInfo2.setText(event+"");
    }


    @Override
    protected void onStop() {
        super.onStop();
        //取消EventBus
        EventBus.getDefault().unregister(this);
    }


}
