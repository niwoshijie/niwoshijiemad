package mainpagers.spackage;

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
public class SPager extends BasePager {

    @ViewInject(R.id.s_recycle)
    private RecyclerView s_recycle;

    public SPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view =View.inflate(context, R.layout.s_layout,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {

    }

}
