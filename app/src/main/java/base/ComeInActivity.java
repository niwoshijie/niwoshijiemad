package base;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import liushaobo.mad.R;
import mainpagers.cpackage.quickpublish.QuickActivity;

public class ComeInActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    @Override
    public void initView() {
        recyclerView = new RecyclerView(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.generateDefaultLayoutParams();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recycleAdapter = new RecycleAdapter(this);
        recyclerView.setAdapter(recycleAdapter);
        setContentView(recyclerView);
    }

    @Override
    public void setListener() {}

    @Override
    protected void initData() {
        recycleAdapter.setOnItemClickLinster(new RecycleAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startIntent(QuickActivity.class);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
                        break;
                }
            }
        });
    }

    /**
     * 切换Activity
     * @param nextOne
     */
    public void startIntent(Class nextOne){
        Intent intent = new Intent(ComeInActivity.this,nextOne);
        startActivity(intent);
    }

}
