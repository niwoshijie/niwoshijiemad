package mainpagers.mpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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
        View view =View.inflate(context, R.layout.m_layout,null);
        x.view().inject(this,view);
        return view;
    }


    @Override
    public void initData() {

    }




}
