package mainpagers.cpackage.quickpublish.pager;

import android.content.Context;
import android.view.View;

import liushaobo.mad.R;
import views.textviews.ScrollTextView;


public class WeiChatTextPager extends BaseWeiChatPager{

    private ScrollTextView autofitTextView;


    public WeiChatTextPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.wei_chat_text, null);
        autofitTextView = (ScrollTextView) view.findViewById(R.id.weichat_auto_text);
        return view;
    }

    @Override
    public void initData(String showContent) {
        autofitTextView.removeHandler();
        autofitTextView.setText(showContent);
    }






}
