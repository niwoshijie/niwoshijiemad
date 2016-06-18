package mainpagers.cpackage.selfserviceapp.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import liushaobo.mad.R;


/**
 * Created by LiuShao on 2016/4/14.
 */
public class StoreHeadPagerAdapter extends PagerAdapter {

    private List<String> imageList;
    private Context context;

    public StoreHeadPagerAdapter(Context context,List<String> imageList){
        this.context = context;
        this.imageList = imageList;
    }

    public void setImageList(List<String> imageList){
        this.imageList = imageList;
    }

    public int[] iamges = new int[]{R.mipmap.back_first,R.mipmap.back_second,R.mipmap.btn_cancel};

    @Override
    public int getCount() {
        return iamges.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView image = new ImageView(context);
        image.setImageResource(iamges[position]);
        container.addView(image, 0);
        return image;
    }
}
