package mainpagers;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LiuShao on 2016/2/16.
 */
public class MainPagerAdapter extends android.support.v4.view.PagerAdapter{

    public  List<BasePager> basePagers;

    public MainPagerAdapter(List<BasePager> basePagers){
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = basePagers.get(position).getRootView();
        container.addView(view, 0);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }




}
