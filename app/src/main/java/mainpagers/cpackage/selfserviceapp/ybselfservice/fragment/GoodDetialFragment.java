package mainpagers.cpackage.selfserviceapp.ybselfservice.fragment;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.beans.LeftGoodsBean;
import mainpagers.cpackage.selfserviceapp.beans.RightGoodsBean;
import mainpagers.cpackage.selfserviceapp.utils.animationutils.CircleAnimationUtil;
import mainpagers.cpackage.selfserviceapp.utils.logutils.LogUtils;
import mainpagers.cpackage.selfserviceapp.views.BottomViewAdapter;
import mainpagers.cpackage.selfserviceapp.views.LeftListViewAdapter;
import mainpagers.cpackage.selfserviceapp.views.RightGridViewAdapter;
import mainpagers.cpackage.selfserviceapp.ybselfservice.selfservice.DividerItemDecoration;
import mainpagers.cpackage.selfserviceapp.ybselfservice.selfservice.PointerPopupWindow;
import mainpagers.cpackage.selfserviceapp.ybselfservice.selfservice.ScreenUtils;

@ContentView(R.layout.fragmet_good_detial)
public class GoodDetialFragment extends BaseFragment {
    private GoodEnsureFragment goodEnsureFragment;

    @ViewInject(R.id.left_goods_view)
    private ListView left_goods_view;

    @ViewInject(R.id.right_goods_view)
    private RecyclerView right_goods_view;

    @ViewInject(R.id.bottom_goods_view)
    private RecyclerView bottom_goods_view;

    @ViewInject(R.id.iv_cart)
    private ImageView iv_cart;

    @ViewInject(R.id.tv_dinner_count_desc)
    private TextView tv_dinner_count_desc;


    @ViewInject(R.id.tv_dinner_type_desc)
    private TextView tv_dinner_type_desc;

    private LeftListViewAdapter leftListViewAdapter;
    private List<LeftGoodsBean> goodsBeans = new ArrayList<>();


    public static int LEFT_SELECT_SELECTION = 0;
    public static int RIGHT_SELECT_SELECTION = 0;
    public static int BOTTOM_SELECT_SELECTION = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            goodEnsureFragment = (GoodEnsureFragment) getActivity().getSupportFragmentManager().findFragmentByTag(GOODSENSURE_TAG);
        }
        if(goodEnsureFragment == null){
            goodEnsureFragment = new GoodEnsureFragment();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLeftMenu();
        initRightLayout();
        initBottonLayout();
        left_goods_view.setDivider(null);
    }

    /**
     * 初始化底部视图
     */
    private BottomViewAdapter bottomViewAdapter ;
    private void initBottonLayout() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bottom_goods_view.setLayoutManager(layoutManager);
        bottomViewAdapter = new BottomViewAdapter(getActivity());
    }


    /**
     * 初始化右面的单个商品详情页面
     */
    private RightGridViewAdapter rightGridViewAdapter, popWindowAdapter;

    private void initRightLayout() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        right_goods_view.setLayoutManager(layoutManager);
        rightGridViewAdapter = new RightGridViewAdapter(getContext());
    }

    /**
     * 初始化左边视图
     */
    private void initLeftMenu() {
        LeftGoodsBean leftGoodsBean = new LeftGoodsBean();
        leftGoodsBean.imageUrl = "";
        leftGoodsBean.textString = "庆森";
        LeftGoodsBean leftGoodsBean1 = new LeftGoodsBean();
        leftGoodsBean1.imageUrl = "";
        leftGoodsBean1.textString = "小乔";
        goodsBeans.add(leftGoodsBean);
        goodsBeans.add(leftGoodsBean1);
        leftListViewAdapter = new LeftListViewAdapter(goodsBeans, getActivity());
        left_goods_view.setAdapter(leftListViewAdapter);
    }

    private int mCurrentLeftPosition;
    @Event(value = R.id.left_goods_view, type = AdapterView.OnItemClickListener.class)
    private void onImageItemClick(AdapterView<?> parent, View view, int position, long id) {
        leftListViewAdapter.setPosition(position);

        initRightMenu(position);
        LogUtils.e("左侧点击", "---------");
    }

    /**
     * 初始化右边视图
     * @param position
     */
    private List<RightGoodsBean> rightGoodsBeansList, popWinBeanList;

    private void initRightMenu(int position) {
        //根据位置信息网络请求获取商品列表

        //解析数据信息填充界面
        rightGoodsBeansList = new ArrayList<>();

        for(int i =0;i<16;i++){
            RightGoodsBean goodsBean = new RightGoodsBean();
            goodsBean.goodsName = "小乔烧饼"+i;
            goodsBean.goodsTitle = "烧饼"+i;
            rightGoodsBeansList.add(goodsBean);
        }

        rightGridViewAdapter.setRightList(rightGoodsBeansList);
        right_goods_view.setAdapter(rightGridViewAdapter);

        //右侧item的点击事件
        rightGridViewAdapter.setOnItemClickLinster(new RightGridViewAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
//                if (rightGoodsBeansList.get(position).goodsName.equals("殷孝桥")) {
                    showPopupWindow(view,position);
//                } else {
//                    if (position != 0) {
//                        showAnimation(view, iv_cart);
//                    }
//                }
            }
        });
    }

    /**
     * 购物车动画
     * @param fromView
     * @param toView
     */
    private void showAnimation(View fromView, ImageView toView) {
        CircleAnimationUtil util = new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(fromView).setDestView(toView);
        util.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
//              mAdapter.removeItem(position);
                upDataBottomView();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        util.startAnimation();
    }

    /**
     * 添加购物车时，动画播放完成更新底部视图
     */
        private void upDataBottomView() {

        }

    private PointerPopupWindow pointerPopupWindow;
    private int[] baseViewLocation = new int[2];
    /**
     * 显示的弹出框
     */
    private void showPopupWindow(View basedView,int location) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, null);
        RecyclerView recyclerpopView = (RecyclerView) view.findViewById(R.id.pop_layout_goods);

        RightGoodsBean rightGoodsBean = new RightGoodsBean();
        rightGoodsBean.goodsTitle = "popwindowd";
        rightGoodsBean.goodsName = "popwindow";
        RightGoodsBean rightGoodsBean1 = new RightGoodsBean();
        rightGoodsBean1.goodsTitle = "popwindowd2";
        rightGoodsBean1.goodsName = "popwindow2";

        RightGoodsBean rightGoodsBean2 = new RightGoodsBean();
        rightGoodsBean2.goodsTitle = "popwindowd2";
        rightGoodsBean2.goodsName = "popwindow2";

        popWinBeanList = new ArrayList<>();
        popWinBeanList.add(rightGoodsBean);
        popWinBeanList.add(rightGoodsBean1);
        popWinBeanList.add(rightGoodsBean2);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerpopView.setLayoutManager(layoutManager);
        popWindowAdapter = new RightGridViewAdapter(getContext());
        popWindowAdapter.setRightList(popWinBeanList);
        recyclerpopView.addItemDecoration(new DividerItemDecoration(15));
        recyclerpopView.setAdapter(popWindowAdapter);
        popWindowAdapter.setOnItemClickLinster(new RightGridViewAdapter.OnItemClickLinster() {
            @Override
            public void onItemClick(View view, int position) {
                showAnimation(view, iv_cart);
            }
        });

        pointerPopupWindow = new PointerPopupWindow(getActivity(),800,200);

        if(baseViewLocation[1]>ScreenUtils.getScreenHeight()/2){
            pointerPopupWindow.setPointerImageRes(R.mipmap.ic_popup_pointer_up);
            pointerPopupWindow.setIsShowUp(true);
        }else{
            pointerPopupWindow.setPointerImageRes(R.mipmap.ic_popup_pointer_down);
            pointerPopupWindow.setIsShowUp(false);
        }
        pointerPopupWindow.setContentView(view);

        //阴影遮罩
        ColorDrawable dw = new ColorDrawable(0x00000000);
        pointerPopupWindow.setBackgroundDrawable(dw);

        basedView.getLocationOnScreen(baseViewLocation);
        if(baseViewLocation[1]>ScreenUtils.getScreenHeight()/2){
            if(location%3==0){
                pointerPopupWindow.showAsPointer(basedView,800,-30);
                Log.e("position",0+"");
            }else if(location%3==1){
                pointerPopupWindow.showAsPointer(basedView,800,-30);
                Log.e("position", 1 + "");
            }else if(location%3==2){
                pointerPopupWindow.showAsPointer(basedView,0,-30);
                Log.e("position", 2 + "");
            }
        }else{
            if(location%3==0){
                pointerPopupWindow.showAsPointer(basedView,800,-30);
                Log.e("position",0+"");
            }else if(location%3==1){
                pointerPopupWindow.showAsPointer(basedView,0,-30);
                Log.e("position", 1 + "");
            }else if(location%3==2){
                pointerPopupWindow.showAsPointer(basedView,0,-30);
                Log.e("position", 2 + "");
            }
        }




        ScreenUtils.backgroundAlpha(getActivity(), 0.5f);
        pointerPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.backgroundAlpha(getActivity(), 1);
            }
        });


    }

    //点击按钮的效果显示
    @Event(value={R.id.btn_cancle_order,R.id.btn_ok_order},type =View.OnClickListener.class)
    private void btn_click(View view){
        switch(view.getId()){
            case R.id.btn_cancle_order:

                break;
            case R.id.btn_ok_order:

                break;
        }
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }



}
