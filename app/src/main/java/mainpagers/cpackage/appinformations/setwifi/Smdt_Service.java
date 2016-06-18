package mainpagers.cpackage.appinformations.setwifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class Smdt_Service extends Service {
    WifiAdmin wifiAdmin;
    private static boolean smdt = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<XmlBean> lists;
    private Context context;
    private MyThread checkThread;
    private int i = 0;

    @Override
    public void onCreate() {
        if(!isWifiConnected()){
            smdt = true;
            wifiAdmin = new WifiAdmin(this);
            context = this;
            if (!XmlParseUtil.getXmlParseUtil().fileIsExists("/mnt/sdcard")) {

            } else {
                lists = XmlParseUtil.getXmlParseUtil().getItem();
                checkThread = new MyThread();
                checkThread.start();
            }
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if(!isWifiConnected()){
            if (checkThread != null) {
                Log.e("usbto", "checkThread.isAlive()+" + checkThread.isAlive());
                if (checkThread.isAlive()) {

                } else {
                    i = 0;
                    lists = XmlParseUtil.getXmlParseUtil().getItem();
                    smdt = true;
                    checkThread.run();
                }
            } else {
                if (!XmlParseUtil.getXmlParseUtil().fileIsExists("/mnt/sdcard")) {
                    lists = XmlParseUtil.getXmlParseUtil().getItem();
                    i = 0;
                    smdt = true;
                    checkThread = new MyThread();
                    checkThread.start();
                }
            }
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {

            while (smdt) {
                try {
                    if(i< lists.size()){
                        addWifiConnect(i);
                        i++;
                    }else{
                        smdt = false;
                    }
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
    }

    private void addWifiConnect(int i) {

        if(!isWifiConnected()){
            wifiAdmin.openWifi();
            String user = lists.get(i).getWifi();
            String pwd = lists.get(i).getPwd();
            wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo(user.trim(), pwd.trim(), 3));
        }else{
            smdt = false;

        }
    }

    private boolean isWifiConnected(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.e("usbto", "mWifi.isConnected()" + mWifi.isConnected());
        return mWifi.isConnected();
    }


}
