package first;

import android.content.Context;
import android.util.AttributeSet;

import views.pagers.LazyViewPager;

/**
 * Created by LiuShao on 2016/1/27.
 */
public class MyViewPager extends LazyViewPager {

    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


}
