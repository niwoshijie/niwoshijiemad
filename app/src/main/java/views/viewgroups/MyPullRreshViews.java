package views.viewgroups;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import liushaobo.mad.R;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyPullRreshViews extends ListView {
    //下拉刷新的头部
    private LinearLayout headLinnerLayout;
    private TextView textView;
    private int refreshHeadHeight;
    private int refreshFootHeight;
    private TextView tv_footView;
    private LinearLayout footLinnerLayout;

    public MyPullRreshViews(Context context) {
        this(context,null);
    }
    public MyPullRreshViews(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private View firstView;
    public void setFirstView(View firstView) {
        this.firstView = firstView;
    }

    private static final String TAG = "MyPullRreshViews";

    public MyPullRreshViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        headLinnerLayout = (LinearLayout) View.inflate(getContext(), R.layout.c_pager_uerdefine_pullrefresh_head,null);
        textView = (TextView) headLinnerLayout.findViewById(R.id.refresh_head_tv);

        this.addHeaderView(headLinnerLayout);

        footLinnerLayout = (LinearLayout) View.inflate(getContext(),R.layout.c_pager_uerdefine_pullrefresh_foot,null);
        tv_footView = (TextView) footLinnerLayout.findViewById(R.id.refresh_foot_tv);

        headLinnerLayout.measure(0,0);
        refreshHeadHeight = headLinnerLayout.getMeasuredHeight();

        Log.d(TAG, "MyPullRreshViews:refreshHeadHeight "+refreshHeadHeight);

        footLinnerLayout.measure(0,0);
        refreshFootHeight = footLinnerLayout.getMeasuredHeight();
        Log.d(TAG, "MyPullRreshViews:refreshFootHeight "+refreshFootHeight);
        headLinnerLayout.setPadding(0,-refreshHeadHeight,0,0);
        footLinnerLayout.setPadding(0,-refreshFootHeight,0,0);
        this.addFooterView(footLinnerLayout);

        this.setOnScrollListener(onScrollListener);
    }


    /**
     * 下拉刷新
     */
    private final int STATE_PULL_REFRESH = 100;

    /**
     * 满足条件后，释放刷新
     */
    private final int STATE_RELEASE_REFRESH = 101;

    /**
     * 正在刷新的状态
     */
    private final int STATE_REFRESH_ING = 102;

    /**
     * 默认状态，下拉刷新
     */
    private int currState = STATE_PULL_REFRESH;
    private boolean isAdding = false;//底部上啦加载是否存在


    private OnScrollListener onScrollListener = new OnScrollListener() {
        // 当listView 滑动状态，发生改变时，调用
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int lastPosition = getLastVisiblePosition();
            int count = getAdapter().getCount()-1;
            if(lastPosition == count&&!isAdding){
                isAdding = true;
                // 将脚显示出来
                footLinnerLayout.setPadding(0, 0, 0, 0);
                if(onrefresh!=null){
                    onrefresh.loadMore();
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            currentPositionItem = firstVisibleItem;
        }
    };

    private int touchY;

    private int currentPositionItem;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                int[] location = new int[2];
                firstView.getLocationInWindow(location);
                int thisy = location[1];

                headLinnerLayout.getLocationInWindow(location);
                int headY = location[1];
                if(thisy>headY){
                    touchY = -1;
                    break;
                }
                if(touchY == -1){
                    touchY = (int) ev.getY();
                    break;
                }

                int paddingTop = (int) (- refreshHeadHeight + ev.getY()-touchY);

                if(currentPositionItem == 0 && paddingTop> -refreshHeadHeight){
                    headLinnerLayout.setPadding(0,paddingTop,0,0);
                    if(paddingTop>0 &&currState == STATE_PULL_REFRESH ){
                        currState = STATE_RELEASE_REFRESH;
                        // 刷新状态 // 执行动画
                        flushState();
                    }
                    if(paddingTop<0 && currState == STATE_REFRESH_ING){
                        currState = STATE_PULL_REFRESH;
                        flushState();
                    }
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:

                int[] currentLocation = new int[2];

                if(currState == STATE_RELEASE_REFRESH){
                    currState = STATE_REFRESH_ING;
                    Log.d(TAG, "onTouchEvent:this.getY() "+MyPullRreshViews.this.getY());
                    Log.d(TAG, "onTouchEvent:refreshHeadHeight "+refreshHeadHeight);
                    headLinnerLayout.getLocationInWindow(currentLocation);
                    if(currentLocation[1]>refreshHeadHeight){
                        MyPullRreshViews.this.scrollTo(0,refreshHeadHeight);
                    }
                    if(onrefresh!=null){
                        onrefresh.loadMore();
                    }
                    flushState();
                }

                if(currState == STATE_PULL_REFRESH){
                    currState = STATE_RELEASE_REFRESH;

                    if(MyPullRreshViews.this.getY()>refreshHeadHeight){
                        MyPullRreshViews.this.scrollTo(0,refreshHeadHeight);
                    }
                    headLinnerLayout.setPadding(0,-refreshHeadHeight,0,0);
                }
                touchY = -1;
                break;

        }
        return super.onTouchEvent(ev);
    }

    public void freshFinsh(){
        if(currState == STATE_REFRESH_ING){
            currState = STATE_PULL_REFRESH;
            textView.setText("下拉刷新");
            headLinnerLayout.setPadding(0,-refreshHeadHeight,0,0);
        }

        if(isAdding){
            isAdding = false;
            footLinnerLayout.setPadding(0,-refreshFootHeight,0,0);
        }

    }


    private void flushState() {
        switch (currState){
            case STATE_PULL_REFRESH:
                textView.setText("下拉刷新");

                break;
            case STATE_REFRESH_ING:
                textView.setText("正在刷新……");

                break;
            case STATE_RELEASE_REFRESH:
                textView.setText("松手刷新……");

                break;

        }
    }

    private OnRefreshComplate onrefresh;

    public void setOnrefresh(OnRefreshComplate onrefresh) {
        this.onrefresh = onrefresh;
    }

    public interface OnRefreshComplate{
        void onfinish();
        void loadMore();
    }

}
