package mainpagers.cpackage.selfserviceapp.ybselfservice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.views.StoreHeadPagerAdapter;
import mainpagers.cpackage.selfserviceapp.ybselfservice.fragment.DinnerTypeFragment;


@ContentView(R.layout.activity_detial)
public class DetialActivity extends BaseActivity {

    private DinnerTypeFragment dinnerTypeFragment;
    @ViewInject(R.id.fl_goods_detial)
    private FrameLayout fl_goods_detial;

    @ViewInject(R.id.view_pager_storehead)
    private ViewPager view_pager_storehead;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (savedInstanceState != null) {
            dinnerTypeFragment = (DinnerTypeFragment) getSupportFragmentManager().findFragmentByTag("dinnerType");
        }
        if (dinnerTypeFragment == null) {
            dinnerTypeFragment = new DinnerTypeFragment();
        }

        object = null;

        initData();
        changeFragment();
    }



    public void changeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_goods_detial, dinnerTypeFragment, "dinnerType").addToBackStack("dinnerType").commit();
    }

    private void initData() {
        handViewPager();
    }


    private int headViewNum = 0;
    private StoreHeadPagerAdapter storeHeadPagerAdapter;
    private List<String> imageUrlList;
    private final int MSG_TIME = 3000111;
    private int cur_index = 0;
    private void handViewPager(){
        imageUrlList = new ArrayList<>();
        storeHeadPagerAdapter = new StoreHeadPagerAdapter(context,imageUrlList);
        view_pager_storehead.setAdapter(storeHeadPagerAdapter);
        headViewNum = storeHeadPagerAdapter.getCount();
        mhHandler.removeMessages(MSG_TIME);
        mhHandler.sendEmptyMessageDelayed(MSG_TIME, 3000);
    }
    private Handler mhHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_TIME:
                    cur_index = cur_index % headViewNum;
                    view_pager_storehead.setCurrentItem(cur_index);
                    cur_index++;
                    mhHandler.sendEmptyMessageDelayed(MSG_TIME,3000);
                    break;
            }
        }
    };


    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    private Object object;


}

