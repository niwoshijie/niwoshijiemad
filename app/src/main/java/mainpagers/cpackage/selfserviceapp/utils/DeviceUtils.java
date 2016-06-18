package mainpagers.cpackage.selfserviceapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.UUID;

import base.APP;
import mainpagers.cpackage.selfserviceapp.utils.caches.CacheUtils;


/**
 * Created by LiuShao on 2016/4/11.
 */

public class DeviceUtils {

    private String sbDeviceId = "";

    /**
     * 获取设备唯一编号
     * @return
     */
    public  String getDeviceNo() {
        sbDeviceId = CacheUtils.getDeviceNo();
        if (TextUtils.isEmpty(sbDeviceId)|| sbDeviceId.equals("-1")) {
            sbDeviceId = getMacAddress();
            return sbDeviceId;
        } else {
            return sbDeviceId;
        }
    }

    public  static String getMacAddress() {
        String macAddress = null;
        WifiManager wifiManager = (WifiManager) APP.mAppApplication.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

        if (!wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        if (macAddress != null) {
            String mac = macAddress.toUpperCase();
            String macS = "";
            for (int i=mac.length()-1;i>=0;i--) {
                macS += mac.charAt(i);
            }
            UUID uuid2 = new UUID(macS.hashCode(), mac.hashCode());

            CacheUtils.setDeviceNo(uuid2.toString());

            return uuid2.toString();
        } else {
            return "-1";
        }
    }



        public static String getDeviceInfo(){
            String phoneInfo = "Product: " + android.os.Build.PRODUCT+"\n";
            phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI+"\n";
            phoneInfo += ", TAGS: " + android.os.Build.TAGS+"\n";
            phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE+"\n";
            phoneInfo += ", MODEL: " + android.os.Build.MODEL+"\n";
            phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK+"\n";
            phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE+"\n";
            phoneInfo += ", DEVICE: " + android.os.Build.DEVICE+"\n";
            phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY+"\n";
            phoneInfo += ", BRAND: " + android.os.Build.BRAND+"\n";
            phoneInfo += ", BOARD: " + android.os.Build.BOARD+"\n";
            phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT+"\n";
            phoneInfo += ", ID: " + android.os.Build.ID+"\n";
            phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER+"\n";
            phoneInfo += ", HARDWARE: " + Build.HARDWARE+"\n";
            phoneInfo += ", RADIO: " + Build.RADIO+"\n";
            phoneInfo += ", USER: " + android.os.Build.USER+"\n";
            phoneInfo +=  getBaseband_Ver()+"\n";
            phoneInfo +=  getLinuxCore_Ver()+"\n";
            phoneInfo +=  getInner_Ver()+"\n";
            return phoneInfo;
        }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */
    public static String getBaseband_Ver(){
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[] { String.class,String.class });
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            Version = (String)result;
        } catch (Exception e) {
        }
        return Version;
    }

    /**
     * CORE-VER
     * 内核版本
     * return String
     */

    public static String getLinuxCore_Ver() {
        Process process = null;
        String kernelVersion = "";
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");

        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);
        String result = "";
        String line;
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            if (result != "") {
                String Keyword = "version ";
                int index = result.indexOf(Keyword);
                line = result.substring(index + Keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void TextMem(){

        //堆内存大小
        ActivityManager manager = (ActivityManager) APP.mAppApplication.getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getLargeMemoryClass();

        //最大内存大小
        int maxMemory = ((int) Runtime.getRuntime().maxMemory())/1024/1024;
        //应用程序已获得内存
        long totalMemory = ((int) Runtime.getRuntime().totalMemory())/1024/1024;
        //应用程序已获得内存中未使用内存
        long freeMemory = ((int) Runtime.getRuntime().freeMemory())/1024/1024;

        System.out.println("---> maxMemory=" + maxMemory + "M,totalMemory=" + totalMemory + "M,freeMemory=" + freeMemory + "M");

    }



    /**
     * INNER-VER
     * 内部版本
     * return String
     */

    public static String getInner_Ver(){
        String ver = "" ;

        if(android.os.Build.DISPLAY .contains(android.os.Build.VERSION.INCREMENTAL)){
            ver = android.os.Build.DISPLAY;
        }else{
            ver = android.os.Build.VERSION.INCREMENTAL;
        }
        return ver;

    }


















}
