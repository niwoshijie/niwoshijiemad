package mainpagers.cpackage.userdefinepagerindicator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import liushaobo.mad.R;
import views.viewgroups.MyPullRreshViews;

public class VpSimpleFragment extends Fragment {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";

    private MyPullRreshViews myPullRreshViews;

    private List datasList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
        }

        for (int i = 0 ;i<50;i++){
            datasList.add(mTitle+"许金金美女"+i);
        }

        View view = View.inflate(getContext(), R.layout.c_pager_userdifine_pagerindicator,null);
        myPullRreshViews = (MyPullRreshViews) view.findViewById(R.id.pull_refresh_view);
        myPullRreshViews.setOnrefresh(new MyPullRreshViews.OnRefreshComplate() {
            @Override
            public void onfinish() {

            }

            @Override
            public void loadMore() {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
                            flushHandler.sendEmptyMessage(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        myPullRreshViews.setAdapter(pagerIndicator);
        myPullRreshViews.setFirstView(pagerIndicator.getView(0,null,null));
        return view;
    }

    public static VpSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private BaseAdapter pagerIndicator = new BaseAdapter() {
        @Override
        public int getCount() {
            return datasList.size();
        }

        @Override
        public Object getItem(int position) {
            return datasList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setText((String)datasList.get(position));
            return textView;
        }
    };


    private Handler flushHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myPullRreshViews.freshFinsh();
        }
    };

}
