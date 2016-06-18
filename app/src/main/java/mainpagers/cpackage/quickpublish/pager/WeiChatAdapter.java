package mainpagers.cpackage.quickpublish.pager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WeiChatAdapter extends PagerAdapter {

    public List<BaseWeiChatPager> baseWeiChatPagers;

    public WeiChatAdapter(List<BaseWeiChatPager> baseWeiChatPagers){
        this.baseWeiChatPagers = baseWeiChatPagers;
    }

    @Override
    public int getCount() {
        return baseWeiChatPagers.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = baseWeiChatPagers.get(position).getRootView();
        container.addView(view, 0);
        return view;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
