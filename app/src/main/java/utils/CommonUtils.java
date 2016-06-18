package utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import base.APP;

public class CommonUtils {


    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static File file;

    public static void copyToSD(Context context, int id, String path) {

        InputStream is = null;
        FileOutputStream fos = null;

        try {
            File dbFile = new File(path);
            if (!dbFile.exists()) {
                is = context.getResources().openRawResource(id);
                fos = new FileOutputStream(dbFile);

                byte[] buffer = new byte[8 * 1024];// 8K
                while (is.read(buffer) > 0)// >
                {
                    fos.write(buffer);
                }
            }

        } catch (Exception e) {

        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //得到本地IP地址（WIFI）
    private String GetLocalIP() {
        WifiManager wifiManager = (WifiManager) APP.mAppApplication.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    /**
     * 获取本机的ip地址
     */
    public static String getIpAdress() {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) APP.mAppApplication.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 获取CPU最大频率，单位KHZ
     *
     * @return
     */
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }


    // 获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //CPU个数
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());

            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception

            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * 旋转屏幕的方向并重启
     *
     * @param value
     */
    public void rotateSystem(String value) {
        Process process = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("setprop persist.sys.hwrotation " + value + " \n");
            os.writeBytes("reboot \n");
            os.writeBytes("exit\n");
            os.flush();
            int aa = process.waitFor();
            is = new DataInputStream(process.getInputStream());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String out = new String(buffer);
            Log.i("tag", out + aa);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }


    public static String getMemoryTotalSize() {
        File innerCardFile = Environment.getExternalStorageDirectory();
        long totalSize = innerCardFile.getTotalSpace();
        String sdTotalStr = Formatter.formatFileSize(APP.mAppApplication, totalSize);
        return sdTotalStr;
    }

    public static String getMemoryUsedSize() {
        File innerCardFile = Environment.getExternalStorageDirectory();
        long totalSize = innerCardFile.getTotalSpace();
        long freeSize = innerCardFile.getFreeSpace();
        long usedSize = totalSize - freeSize;
        String sdusedSize = Formatter.formatFileSize(APP.mAppApplication, usedSize);
        return sdusedSize;
    }

    //获取内置sd卡的总大小
    public static String getInnerSdSize() {
        File sdTotal = Environment.getDataDirectory();
        long totalSdSize = sdTotal.getTotalSpace();
//        String innerTotalSdSize = Formatter.formatFileSize(APP.mAppApplication, totalSdSize);
        return String.valueOf(totalSdSize/(1024*1024));
    }

    //获取内部存储剩余量
    public static String getInnerSdUsedSize() {
        File sdTotal = Environment.getDataDirectory();
        long totalSdSize = sdTotal.getTotalSpace();
        long freeSdSzie = sdTotal.getFreeSpace();
        long usedSize = (totalSdSize - freeSdSzie)/1024/1024;

//      String innerusedSize = Formatter.formatFileSize(APP.mAppApplication, usedSize);
        return String.valueOf(usedSize)+"MB";
    }



    /**
     * 获取外置SD卡路径,暂时不能用
     * @return	应该就一条记录或空
     */
    public static List getExtSDCardPath()
    {
        List lResult = new ArrayList();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard"))
                {
                    String [] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory())
                    {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }



    public static final String[] extsdsNoUsb = new String[]{"extsd", "external_sd"};

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

    public static String sdfile() {
        String sdfiles = "";
        StorageManager storageManager = (StorageManager) APP.mAppApplication.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                if (i == 1) {
                    sdfiles = ((String[]) invoke)[i].toString();
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return sdfiles;
    }


    /**
     * 获取手机内部剩余存储空间
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取SDCARD剩余存储空间
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCARD总的存储空间
     * @return
     */
    public static long getTotalExternalMemorySizes() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取系统总内存
     * @param context 可传入应用程序上下文。
     * @return 总内存大单位为B。
     */
    public static long getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

    /**
     * 单位换算
     * @param size 单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }



    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static  String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(APP.mAppApplication, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(APP.mAppApplication, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     * @return
     */
    public static String getRomTotalSize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(APP.mAppApplication, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    public static String getRomAvailableSize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(APP.mAppApplication, blockSize * availableBlocks);
    }



















    /**
     * 根据字符串传递的类型转换到制定格式
     * @param cale
     * @param format
     * @return
     */
    public static String dateToStrFormat (Calendar cale, String format) {
        //将Calendar类型转换成Date类型
        Date tasktime = cale.getTime();
        //设置日期输出的格式
        SimpleDateFormat df=new SimpleDateFormat(format);
        return df.format(tasktime);
    }

    /**
     * 给定时间转换成Calender
     * @param times
     * @return
     */
    public static Calendar strToDateFormat (String times) {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(times);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


    public static void execSuCmd(String cmd) {
        Process process = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            int aa = process.waitFor();
            is = new DataInputStream(process.getInputStream());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String out = new String(buffer);
            Log.i("tag", out + aa);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }

    public static void deleteAllFileFolder(final String path) {
        HandleMessageUtils.getInstance().runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    File filePath = new File(path);
                    if (!filePath.exists() && !filePath.isDirectory()) {
                        filePath.mkdirs();
                    }
                    File[] files = filePath.listFiles();
                    if (files.length > 0) {
                        for (int i = 0; i < files.length; i++) {
                            File file = files[i];
                            file.delete();
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
    }



    /**
     * 获取当前手机版本
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String name = appInfo.metaData.getString("version_name");
            if (name != null) {
                return name;
            }
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 渠道信息
     * @param context
     * @param metaName
     * @return
     */
    public static int getChannel(Context context, String metaName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(metaName);

        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 隐藏输入键盘
     * @param view
     * @param context
     */
    public static void hideSoftInput(EditText view, Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }




}
