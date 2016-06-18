package utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import base.APP;

/**
 * Created by LiuShao on 2016/2/22.
 */
public class NetWorkUtils {
    /**
     * 获取本机的ip地址
     */
    public static String  getIpAdress(){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) APP.mAppApplication.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return  ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }


    public static String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {

        }
        return null;
    }


    //获取本机MAC地址
    public String getLocalMacAddress(){
        WifiManager wifi = (WifiManager) APP.mAppApplication.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


//    //得到本机IP地址
//    public String getLocalIpAddress(){
//        try{
//            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//            while(en.hasMoreElements()){
//                NetworkInterface nif = en.nextElement();
//                Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
//                while(enumIpAddr.hasMoreElements()){
//                    InetAddress mInetAddress = enumIpAddr.nextElement();
//                    if(!mInetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(mInetAddress.getHostAddress())){
//                        return mInetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        }catch(SocketException ex){
//            Log.e("MyFeiGeActivity", "获取本地IP地址失败");
//        }
//
//        return null;
//    }




}
