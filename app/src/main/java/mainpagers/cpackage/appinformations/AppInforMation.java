package mainpagers.cpackage.appinformations;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Calendar;

import base.BaseActivity;
import broadcastreceivers.AlarmReceiver;
import broadcastreceivers.UsbReceiver;
import liushaobo.mad.R;
import mainpagers.cpackage.appinformations.settimerclock.SetAlarm;
import mainpagers.cpackage.selfserviceapp.utils.DeviceUtils;
import utils.CommonUtils;

/**
 * Created by LiuShao on 2016/3/7.
 */
public class AppInforMation extends BaseActivity{

    @ViewInject(R.id.tv_tv_information)
    private TextView tv_tv_information;

    @ViewInject(R.id.tv_memory_size)
    private TextView tv_memory_size;

    @ViewInject(R.id.tv_memory_sizes)
    private TextView tv_memory_sizes;

    @ViewInject(R.id.idExisted)
    private TextView idExisted;

    @ViewInject(R.id.tv_memory_size2)
    private TextView tv_memory_size2;

    @ViewInject(R.id.tv_clander)
    private TextView tv_clander;

    @ViewInject(R.id.btn_set_sys_all_time)
    private Button btn_set_sys_all_time;

    @ViewInject(R.id.btn_set_sys_date)
    private Button btn_set_sys_date;

    @ViewInject(R.id.btn_set_sys_time)
    private Button btn_set_sys_time;
    @ViewInject(R.id.reboot_system)
    private Button reboot_system;

    @ViewInject(R.id.btn_set_sys_time_second)
    private Button btn_set_sys_time_second;

    @ViewInject(R.id.device_info)
    private TextView device_info;

    @ViewInject(R.id.btn_shoudiantong)
    private Button btn_shoudiantong;

    @Override
    public void initView() {
        setContentView(R.layout.c_app_informations);
        x.view().inject(this);
    }

    @Override
    public void setListener() {}

    private UsbReceiver usbReceiver;

    @Override
    protected void initData() {

        usbReceiver = new UsbReceiver();
        regeditUSBReceiver();


        tv_tv_information.setText("CPU名字:" + CommonUtils.getCpuName()
                + "最大频率:" + CommonUtils.getMaxCpuFreq() + "CPU核心数"
                + CommonUtils.getNumCores());

        tv_memory_size.setText("RAM已用-----------------" + CommonUtils.getMemoryUsedSize() +
                "RAM可用----------------" + CommonUtils.getMemoryTotalSize() +
                "ROM已用--------------" + CommonUtils.getInnerSdUsedSize()
                + "-------------ROM可用" + CommonUtils.getInnerSdSize());

        readSystem();

        tv_memory_sizes.setText("内部剩余-------------" + CommonUtils.formatFileSize(CommonUtils.getAvailableInternalMemorySize(), true)
                + "内部总空间----------------" + CommonUtils.formatFileSize(CommonUtils.getTotalInternalMemorySize(), true) +
                "SD卡剩余------------------" + CommonUtils.formatFileSize(CommonUtils.getAvailableExternalMemorySize(), true) +
                "SD卡总-------------------" + CommonUtils.formatFileSize(CommonUtils.getTotalExternalMemorySizes(), true)
                + "系统内存空间-------------" + romSize + "MB ------------------剩：" + romAvailSize + "MB" +
                "手机内存空间-----------------" + sdCardSize + "MB----------------------剩:"
                + sdCardAvailSize + "MB");



        tv_memory_size2.setText("SD卡存储路径————>" + CommonUtils.getNoUsbSdcardPath()
                + "sd卡总容量" + CommonUtils.getTotalMemorySize(this)
                + "当前可用内存" + CommonUtils.getAvailableMemory(this)
                + "sd卡总大小" + CommonUtils.getSDTotalSize() + "");

        testClander(true);
        testClander(false);
        initBtn();

//        if( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            idExisted.setText(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
//            +"getMemoryTotalSize"+getMemoryTotalSize()+"getMemoryUsedSize"+getMemoryUsedSize());
//        }

        //内部可用大小
        String size = String.valueOf(getAvailaleSize2());

        String outPath  = getNoUsbSdcardPath();

        String outSdSize = getAvailaleSize(outPath);

        idExisted.setText("内部大小："+size+"外部大小"+ outSdSize);

        device_info.setText(DeviceUtils.getDeviceInfo());
    }

    /**
     * 注册监听插入U盘监听
     */
    private void regeditUSBReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_SHARED);// 如果SDCard未安装,并通过USB大容量存储共享返回
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);// 表明sd对象是存在并具有读/写权限
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);// SDCard已卸掉,如果SDCard是存在但没有被安装
        filter.addAction(Intent.ACTION_MEDIA_CHECKING); // 表明对象正在磁盘检查
        filter.addAction(Intent.ACTION_MEDIA_EJECT); // 物理的拔出 SDCARD
        filter.addAction(Intent.ACTION_MEDIA_REMOVED); // 完全拔出
        filter.addDataScheme("file"); // 必须要有此行，否则无法收到广播
        this.registerReceiver(usbReceiver, filter);
    }

    public static String getMemoryTotalSize(){
        File innerCardFile = Environment.getExternalStorageDirectory();
        long totalSize = innerCardFile.getTotalSpace();
        return String.valueOf(totalSize/1024/1024);
    }

    public static final String[] extsdsNoUsb = new String[]{"extsd", "external_sd","sdcard1","usb_storage/USB_DISK1/udisk0"};

    public static String getNoUsbSdcardPath() {
        for (String string : extsdsNoUsb) {
            File file = new File("mnt/" + string);
            if (file.exists()) {
                if (file.canExecute() && file.canWrite() && file.canRead()) {
                    return file.getPath();
                }
            }
        }
        return "";
    }

    public static String getMemoryUsedSize(){
        File innerCardFile = Environment.getExternalStorageDirectory();
        long totalSize = innerCardFile.getTotalSpace();
        long freeSize = innerCardFile.getFreeSpace();
        long usedSize = totalSize - freeSize;
        return String.valueOf(usedSize/1024/1024);
    }


    private void testClander(boolean isRCT) {
        Calendar mCalendar = Calendar.getInstance();
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCalendar.get(Calendar.MINUTE);
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        tv_clander.setText("mHour" + mHour + "mMinute" + mMinute);
        SetAlarm.getInstance().setContext(this);
        if(isRCT){
            SetAlarm.getInstance().setTime(15,59,isRCT);
        }else{
            SetAlarm.getInstance().setTime(15,55,isRCT);
        }
    }

    private void initBtn() {
        btn_set_sys_all_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSystemTime.getInstance().setDateTime(2016,3,23,6,23);
            }
        });
        btn_set_sys_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSystemTime.getInstance().setDate(2016, 3, 23);
            }
        });

        btn_set_sys_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSystemTime.getInstance().setTime(3, 12);
            }
        });
        reboot_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.execSuCmd("reboot");
            }
        });
        btn_set_sys_time_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetSystemTime.getInstance().setTime(2070,5,25,6,20,10);
            }
        });

        btn_shoudiantong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private int romSize;
    private int sdCardSize;
    private int romAvailSize;
    private int sdCardAvailSize;

    private void readSystem() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();

        romSize = (int) (blockSize * blockCount / 1024 / 1024);
        romAvailSize = (int) (blockSize * availCount / 1024 / 1024);

        File sdCardRoot = Environment.getExternalStorageDirectory();
        StatFs sdCardsf = new StatFs(sdCardRoot.getPath());

        long sdCardTotalBlocks = sdCardsf.getBlockCount();
        long sdCardavaliCount = sdCardsf.getAvailableBlocks();
        long sdCardBlockSize = sdCardsf.getBlockSize();

        sdCardAvailSize = (int) (sdCardavaliCount * sdCardBlockSize / 1024 / 1024);
        sdCardSize = (int) (sdCardTotalBlocks * sdCardBlockSize / 1024 / 1024);
    }


    //需要判断sd卡是否存在
    public String getAvailaleSize(String path) {
        if(!TextUtils.isEmpty(path)){
            StatFs stat = new StatFs(path);    /*获取block的SIZE*/
            long blockSize = stat.getBlockSize();    /*空闲的Block的数量*/
            long availableBlocks = stat.getAvailableBlocks();   /* 返回bit大小值*/
            return String.valueOf(availableBlocks * blockSize/1024 /1024);
        }
         //(availableBlocks * blockSize)/1024      KIB 单位    //(availableBlocks * blockSize)/1024 /1024  MIB单位     }
        return "";
    }

    public long getAvailaleSize2() {
        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());    /*获取block的SIZE*/
        long blockSize = stat.getBlockSize();    /*空闲的Block的数量*/
        long availableBlocks = stat.getAvailableBlocks();   /* 返回bit大小值*/
        return availableBlocks * blockSize/1024 /1024;     //(availableBlocks * blockSize)/1024      KIB 单位    //(availableBlocks * blockSize)/1024 /1024  MIB单位     }

    }


    public void registerAlarmManager() {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), Intent.FLAG_ACTIVITY_NEW_TASK);
        long now = System.currentTimeMillis();
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, 5000, pi);
    }

}
