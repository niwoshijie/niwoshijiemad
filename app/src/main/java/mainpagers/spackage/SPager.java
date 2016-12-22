package mainpagers.spackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.ContantValue;
import base.RecycleAdapter;
import base.SpacesItemDecoration;
import liushaobo.mad.R;
import mainpagers.BasePager;

/**
 * Created by LiuShao on 2016/2/16.
 */
public class SPager extends BasePager {

    @ViewInject(R.id.s_recycle)
    private RecyclerView s_recycle;

    public SPager(Context context) {
        super(context);
    }



    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.s_layout,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.generateDefaultLayoutParams();
//      linearLayoutManager.offsetChildrenVertical(5);
        s_recycle.setLayoutManager(linearLayoutManager);
        s_recycle.setItemAnimator(new DefaultItemAnimator());

        int spacingInPixels = 8;
        s_recycle.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        RecycleAdapter recycleAdapter = new RecycleAdapter(context);
        s_recycle.setAdapter(recycleAdapter);
        recycleAdapter.setRecorse(ContantValue.CitemName, ContantValue.imageList);
        recycleAdapter.setOnItemClickLinster(new RecycleAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
                startIntent(ContantValue.Cobjects[position]);
            }
        });
    }


    /**
     * 切换Activity
     * @param class1
     */
    public void startIntent(Class class1){
        Intent intent = new Intent(context,class1);
        context.startActivity(intent);
    }


}
