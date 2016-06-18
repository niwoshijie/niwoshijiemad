package mainpagers.daipaclage;

import android.content.Context;
import android.view.View;

import org.xutils.x;

import liushaobo.mad.R;
import mainpagers.BasePager;

/**
 * Created by LiuShao on 2016/2/16.
 */
public class DAIPager extends BasePager {


    public DAIPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view =View.inflate(context, R.layout.dai_layout,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
    }



}
