package mainpagers.cpackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import base.ContantValue;
import base.RecycleAdapter;
import base.SpacesItemDecoration;
import liushaobo.mad.R;
import mainpagers.BasePager;
/**
 * Created by LiuShao on 2016/2/16.
 */
public class CPager extends BasePager {

    private RecyclerView recyclerView;

    public CPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
       View view =View.inflate(context, R.layout.c_layout,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.c_recycle);
        return view;
    }

    @Override
    public void initData() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.generateDefaultLayoutParams();
//      linearLayoutManager.offsetChildrenVertical(5);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        int spacingInPixels = 8;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        RecycleAdapter recycleAdapter = new RecycleAdapter(context);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.setRecorse(ContantValue.itemName, ContantValue.CimageList);

        recycleAdapter.setOnItemClickLinster(new RecycleAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
                startIntent(ContantValue.objects[position]);
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
