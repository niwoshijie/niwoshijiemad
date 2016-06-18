package mainpagers.dpackage;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ant.liao.GifView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import base.MADConstant;
import liushaobo.mad.R;
import mainpagers.BasePager;

/**
 * Created by LiuShao on 2016/2/16.
 */
public class DAPager extends BasePager {

    GifView gif_test1;
    GifView gif_test2;
    GifView gif_test3;
    mainpagers.cpackage.drawerpackage.GifView gif_test4;
    mainpagers.cpackage.drawerpackage.GifView gif_test5;

    LinearLayout ll_dapager ;
    public DAPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
       View view =View.inflate(context, R.layout.da_layout,null);
        gif_test1 = (GifView) view.findViewById(R.id.gif_test1);
        gif_test2 = (GifView) view.findViewById(R.id.gif_test2);
        gif_test3 = (GifView) view.findViewById(R.id.gif_test3);
        gif_test4 = (mainpagers.cpackage.drawerpackage.GifView) view.findViewById(R.id.gif_test4);
        gif_test5 = (mainpagers.cpackage.drawerpackage.GifView) view.findViewById(R.id.gif_test5);
        ll_dapager = (LinearLayout) view.findViewById(R.id.ll_dapager);
        return view;
    }

    @Override
    public void initData() {
        gif_test1.setGifImage(R.mipmap.set);
        gif_test2.setGifImage(R.mipmap.set);
//      gif_test3.setGifImageType(GifView.GifImageType.WAIT_FINISH);

        gif_test2.setShowDimension(60,60);

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(MADConstant.MAD_PATH+"set.gif"), 16 * 1024);
            is.mark(16 * 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gif_test3.setGifImage(is);

        gif_test4.setMovieFile(MADConstant.MAD_PATH + "niu.gif");

        gif_test5.setMovieResource(R.mipmap.set);

        GifView gifView = new GifView(context);
        gifView.setShowDimension(60,70);
        gifView.setGifImage(is);
        ll_dapager.addView(gifView);

    }
}
