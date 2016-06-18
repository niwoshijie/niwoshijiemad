package mainpagers.cpackage.selfserviceapp.ybselfservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls
     */
    protected void startNextActivity(Activity activity, Class<?> cls) {
        if (activity == null || cls == null) {
            return;
        }
        startActivity(activity, cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     * @param cls
     */
    protected void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        if (activity == null || cls == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * 含有Bundle通过Class带result跳转界面
     **/
    protected void startActivityForResult(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        if (activity == null || cls == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }



}



