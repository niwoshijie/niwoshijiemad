package mainpagers.mpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.SpacesItemDecoration;
import liushaobo.mad.R;
import mainpagers.BasePager;

/**
 * Created by LiuShao on 2016/2/16.
 */
public class MPager extends BasePager {

    public MPager(Context context) {
        super(context);
    }

    @ViewInject(R.id.mao_recycle)
    private RecyclerView twoWayView;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.m_layout,null);
        x.view().inject(this,view);
        return view;
    }

    private String[]  resourse = new String[]{"美女1","美女2","美女3"};
    private int[]  resourseImage = new int[]{R.mipmap.h1,R.mipmap.h2,R.mipmap.h3};

    @Override
    public void initData() {
        twoWayView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MaoAdapter maoAdapter = new MaoAdapter(resourse,resourseImage);
        twoWayView.setAdapter(maoAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        twoWayView.addItemDecoration(decoration);

    }




}
