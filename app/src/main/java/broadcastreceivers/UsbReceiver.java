package broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import mainpagers.cpackage.appinformations.setwifi.WifiConnectDialog;
import mainpagers.cpackage.appinformations.setwifi.XmlParseUtil;


/**
 * Created by LiuShao on 2016/3/18.
 */
public class UsbReceiver extends BroadcastReceiver{

    private XmlParseUtil xmlParseUtil;

    @Override
    public void onReceive(Context context, Intent intent) {
        // intent.getAction());获取存储设备当前状态
        Log.e("usbtoConnectWifi", "BroadcastReceiver:" + intent.getAction());
        // intent.getData().getPath());获取存储设备路径
        Log.e("usbtoConnectWifi", "path:" + intent.getData().getPath());

        if (intent.getAction().equals(intent.ACTION_MEDIA_MOUNTED)) {//具有可读写的sd卡
            WifiConnectDialog.getInstance().setContext(context);
            initUsbToConnectWifi(intent.getData().getPath(),intent);

        } else if (intent.getAction().equals(intent.ACTION_MEDIA_REMOVED)) {//完全拔出

            WifiConnectDialog.getInstance().finishWifiSet();
        }
    }

    private void initUsbToConnectWifi(String wifiPath,Intent intent) {
        xmlParseUtil = XmlParseUtil.getXmlParseUtil();
        xmlParseUtil.setWifiPath(wifiPath);
        final String path = intent.getData().getPath();
        if(!TextUtils.isEmpty(path)){
            if (xmlParseUtil.fileIsExists(path)){//如果U盘中存在wifi配置文件
                //显示窗口提示框
                WifiConnectDialog.getInstance().showWifiDialog();
            } else {

            }
        }
    }


}
