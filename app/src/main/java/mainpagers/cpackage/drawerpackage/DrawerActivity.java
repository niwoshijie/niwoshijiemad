package mainpagers.cpackage.drawerpackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import base.MADConstant;
import liushaobo.mad.R;
import mainpagers.cpackage.drawerpackage.circle.CircleMenuAdapter;
import mainpagers.cpackage.drawerpackage.circle.CircleMenuItem;
import mainpagers.cpackage.drawerpackage.circle.MyCircleMenu;
import utils.BrightnessUtil;
import utils.SpUtils;

/**
 * Created by LiuShao on 2016/2/19.控件案列
 */
public class DrawerActivity extends BaseActivity{

    private SlidingDrawer mDrawer;
    private ImageButton ImageButton;
    private boolean flag;

    @ViewInject(R.id.seekbar1)
    private SeekBar seekbar1;
    private int brightness;

    @ViewInject(R.id.circlemenu)
    private MyCircleMenu circleMenuGroup;

    @ViewInject(R.id.gif1)
    private GifView gif1;

    @ViewInject(R.id.gif2)
    private GifView gif2;

    private String[] mItemTexts = new String[]{"云标捂脸","云标打印","云标会议"};

    private int[] colors = new int[]{R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark};
    private int[] mItemImgs = new int[]{R.mipmap.g1,R.mipmap.g2,R.mipmap.g3};

    private List<CircleMenuItem> mMenuItems = new ArrayList<CircleMenuItem>();

    @Override
    public void initView() {
        setContentView(R.layout.drawer_layout);
        mDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
        ImageButton = (android.widget.ImageButton) findViewById(R.id.handle);
        x.view().inject(this);
    }

    @Override
    public void initValue() {
        initPermission();
    }

    private void initPermission() {
        /**
         * An app can use this method to check if it is currently allowed to write or modify system
         * settings. In order to gain write access to the system settings, an app must declare the
         * {@link android.Manifest.permission#WRITE_SETTINGS} permission in its manifest. If it is
         * currently disallowed, it can prompt the user to grant it this capability through a
         * management UI by sending an Intent with action
         * {@link android.provider.Settings#ACTION_MANAGE_WRITE_SETTINGS}.
         *
         * @param context A context
         * @return true if the calling app can write to system settings, false otherwise
         */
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M){
            if(!Settings.System.canWrite(this)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }

    }

    private int REQUEST_CODE = 123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M){
                if (Settings.System.canWrite(this)) {
                    //检查返回结果
                    Toast.makeText(this, "WRITE_SETTINGS permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "WRITE_SETTINGS permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void initData() {

        gif2.setMovieResource(R.raw.maoyeye);
        String path = MADConstant.MAD_PATH+"niu.gif";
        gif1.setMovieFile(path);

        initCircleMenu();
        initBrightness();
        mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                flag = true;
                ImageButton.setImageResource(R.drawable.penl_down);
            }
        });

        mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                flag = false;
                ImageButton.setImageResource(R.drawable.penl_up);
            }
        });

        mDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollEnded() {

            }

            @Override
            public void onScrollStarted() {

            }
        });

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                brightness = seekBar.getProgress();
                seekBar.setProgress(brightness);
                SpUtils.saveString(DrawerActivity.this, "test", brightness + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                int curProgress = seekBar.getProgress();// 得到当前进度值
                // 当进度小于70时，设置成70，防止太黑看不见的情况。
                if (curProgress < 20) {
                    curProgress = 20;
                }
                // 根据当前进度改变屏幕亮度
                Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, curProgress);
                curProgress = Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, -1);
                BrightnessUtil.setBrightness(DrawerActivity.this,
                        curProgress);
                // BrightnessUtil.saveBrightness(TestScreenBrightnessActivity.this,
                // curProgress);
            }
        });
    }


    /**
     * 初始化环形菜单
     */
    private void initCircleMenu() {
        initCircleMenuData(mItemTexts, mItemImgs);
        //中心视图
        View centerView = LayoutInflater.from(this).inflate(R.layout.circle_menu_item_center,null,false);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        circleMenuGroup = (MyCircleMenu)findViewById(R.id.circlemenu);
        circleMenuGroup.setAdapter(new CircleMenuAdapter(mMenuItems));
        circleMenuGroup.setCenterView(centerView);
        circleMenuGroup.setOnMenuItemClickListener(new MyCircleMenu.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {

            }
        });
    }


    private void initCircleMenuData(String[] mItemTexts, int[] mItemImgs) {
        if (mItemImgs==null && mItemTexts==null){
            throw new IllegalArgumentException("文本和图片不能为空");
        }
        int count = mItemImgs==null ? mItemTexts.length: mItemImgs.length;
        if (mItemImgs!=null && mItemTexts!=null){
            count = Math.min(mItemImgs.length,mItemTexts.length);
        }

        for (int i=0;i<count;i++){
            mMenuItems.add(new CircleMenuItem(mItemImgs[i],mItemTexts[i],colors[i]));
        }
    }

    /**
     * 初始化屏幕亮度值
     */
    private void initBrightness() {
        // 如果开启了自动亮度调节，则关闭之
        if (BrightnessUtil.isAutoBrightness(this)) {
            BrightnessUtil.stopAutoBrightness(this);
        }
        String light = SpUtils.getString(this, "test", "50");
        if (!"".equals(light)) {
            brightness = Integer.valueOf(light);
        } else {
            brightness = BrightnessUtil.getScreenBrightness(this);
        }
        BrightnessUtil.setBrightness(this, brightness);
        seekbar1.setProgress(brightness);
    }


}
