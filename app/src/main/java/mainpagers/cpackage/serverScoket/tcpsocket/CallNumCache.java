package mainpagers.cpackage.serverScoket.tcpsocket;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import utils.ACache;

/**
 * Created by LiuShao on 2016/3/1.
 */
public class CallNumCache {

    public static final String DEVICETYPE_CONSTANT ="devicetype_constant";
    public static final String MAIN_IPADRESS = "main_ipadress";
    public static final String SOCKET_PORT_NUM = "socket_port_num";
    public static final String IS_SINGLE_DEVICE = "is_single_device";

    private static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String CACHE_SAVE_PATH = SD_PATH + "/serverinfo";
    private static ACache acache = ACache.get(new File(CACHE_SAVE_PATH));

    public static String getIsMainServer() {
        if(acache.getAsString(DEVICETYPE_CONSTANT)!=null && !TextUtils.isEmpty(acache.getAsString(DEVICETYPE_CONSTANT))){
            return acache.getAsString(DEVICETYPE_CONSTANT);
        }else{
            return "1";
        }
    }

    public static String getMainIpadress() {
        if(acache.getAsString(MAIN_IPADRESS)!=null&& !TextUtils.isEmpty(acache.getAsString(MAIN_IPADRESS))){
            return acache.getAsString(MAIN_IPADRESS);
        }else{
            return "";
        }
    }

    public static String getSocketPortNum() {
        if(acache.getAsString(SOCKET_PORT_NUM)!=null&&!TextUtils.isEmpty(acache.getAsString(SOCKET_PORT_NUM))){
            return acache.getAsString(SOCKET_PORT_NUM);
        }else{
            return "10000";
        }
    }

    public static String getIsSingleDevice() {
        if(acache.getAsString(IS_SINGLE_DEVICE)!=null&&!TextUtils.isEmpty(acache.getAsString(IS_SINGLE_DEVICE))){
            return acache.getAsString(IS_SINGLE_DEVICE);
        }else{
            return "1";
        }
    }

    public static void putDevicetypeConstant (String serNum) {
        acache.put(DEVICETYPE_CONSTANT, serNum);
    }
    public static void putMainIpadress (String ipadress) {
        acache.put(MAIN_IPADRESS, ipadress);
    }
    public static void putSocketPortNum (String socketnum) {
        if(!TextUtils.isEmpty(socketnum)){
            acache.put(SOCKET_PORT_NUM, socketnum);
        }
    }
    public static void putIsSingleDevice (String isSingle) {
        acache.put(IS_SINGLE_DEVICE, isSingle);
    }

}
