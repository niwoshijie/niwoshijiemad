package mainpagers.cpackage.t23DesignMode.listenercallback;

import android.view.View;
import android.widget.Toast;

import base.APP;

/**
 * Created by LiuShao on 2016/5/12.
 */
public class TestBean extends Mode{


    @Override
    public void onClick(View view) {
        Toast.makeText(APP.mAppApplication,"aaaaa",Toast.LENGTH_SHORT).show();
    }


}
