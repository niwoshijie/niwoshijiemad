package base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.ybselfservice.AppActivityManager;

public abstract class BaseActivity extends AppCompatActivity {


    protected Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.action_blue);//通知栏所需颜色
//        }

        initView();
        mContext = this;
        initData();
        AppActivityManager.getAppActivityManager().addActivity(this);
//      initAllData();
    }

    /**
     * 初始化布局文件中的控件
     */
    public abstract void initView();

    /**
     * 设置控件的监听
     */
    public abstract void initValue();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化总的数据
     */
    private void initAllData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected Toolbar toolbar;

    protected void showToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (title != null) {
                toolbar.setTitle(title);
            }
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            toolbar.showOverflowMenu();
            setSupportActionBar(toolbar);

            if (showNavigationIcon()) {
                toolbar.setNavigationIcon(R.mipmap.g28);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    protected boolean showNavigationIcon() {
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:

                break;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        AppActivityManager.getAppActivityManager().finishActivity(this);
        super.onDestroy();
    }



    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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

}
