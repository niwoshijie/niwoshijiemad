package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.view.View;

import liushaobo.mad.R;
import views.imageviews.photoview.PhotoView;


public class WeiChatImageView extends BaseWeiChatPager{

    private PhotoView photoView;

    public WeiChatImageView(Context context) {
        super(context);
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.weichat_image, null);
        photoView = (PhotoView) view.findViewById(R.id.wei_chat_photo_view);
        photoView.enable();
        return view;
    }

    @Override
    public void initData(String imageUrI) {
        System.gc();

    }
}
