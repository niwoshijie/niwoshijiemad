package mainpagers.cpackage.selfserviceapp.ybselfservice.fragment;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/4/11.
 */
@ContentView(R.layout.fragment_goods_ensure)
public class GoodEnsureFragment extends BaseFragment{

    @ViewInject(R.id.rv_goods_ensure)
    private RecyclerView rv_goods_ensure;


    @Event(R.id.tv_test)
    private void delete(View view){
//    getActivity().getSupportFragmentManager().popBackStack("dinnerType", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

}
