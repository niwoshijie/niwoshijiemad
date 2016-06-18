package mainpagers.cpackage.selfserviceapp.ybselfservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/4/11.
 */
public class BaseFragment extends Fragment {

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    public String GOODS_DETIAL_TAG = "goods";
    public String GOODSENSURE_TAG = "goodsensureTag";
    public String DINER_TYPE_TAG = "diner_type_tag";

    /**
     * 切换fragment
     * @param from 从何处切换
     * @param to   到下一个fragment
     * @param TAG  fragment 的标记
     */
    public void switchContent(Fragment from, Fragment to, String TAG) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            transaction
                    .hide(from)
                    .add(R.id.fl_goods_detial, to, TAG) // 隐藏当前的fragment，add下一个到Activity中
//                  .addToBackStack(TAG)
                    .commit();
        } else {
            transaction
                    .hide(from)
                    .show(to)
                    .commit(); // 隐藏当前的fragment，显示下一个
        }
    }






}
