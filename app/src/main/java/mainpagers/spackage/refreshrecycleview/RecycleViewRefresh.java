package mainpagers.spackage.refreshrecycleview;

import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import base.ContantValue;
import liushaobo.mad.R;

/**
 * Created by feige on 2016/12/21.
 */

public class RecycleViewRefresh extends BaseActivity{

    @ViewInject(R.id.pull_recycle_view)
    private PullRefreshRecyclerView pull_recycle_view;

    @Override
    public void initView() {
        setContentView(R.layout.s_pager_recycleview_horizental);
        x.view().inject(this);
        HorizentalAdapter recycleAdapter = new HorizentalAdapter(this);
        pull_recycle_view.setAdapter(recycleAdapter);
        recycleAdapter.setRecorse(ContantValue.itemName, ContantValue.imageList);
    }

    @Override
    public void initValue() {
        pull_recycle_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }
        });

    }

    @Override
    protected void initData() {

    }


}
