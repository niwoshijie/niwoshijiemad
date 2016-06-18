package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by LiuShao on 2016/2/21.
 */
public class SpUtils {

    private static SharedPreferences sp;
    public  static final String SP_NAME = "config";

    //Ctrl shift U 大小写转换
    public static String C_PAGER_SCOKET_DeviceNum_CONSTANT = "c_pager_scoket_constant";
    public static String C_PAGER_SCOKET_DEVICETYPE_CONSTANT ="c_pager_scoket_devicetype_constant";
    public static String C_PAGER_SCOKET_MAIN_IPADRESS = "c_pager_scoket_main_ipadress";

    public static void saveString(Context context,String key, String value){
        if(sp==null)
            sp = context.getSharedPreferences(SP_NAME,0);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        if(sp==null)
            sp=context.getSharedPreferences(SP_NAME,0);
        return sp.getString(key, defValue);
    }

    public static void saveBoolean(Context context,String key,boolean value){
        if(sp == null)
            sp = context.getSharedPreferences(SP_NAME,0);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context , String key,boolean defValue){
        if(sp == null)
            sp = context.getSharedPreferences(SP_NAME,0);
        return sp.getBoolean(key, defValue);
    }


    public static String SceneList2String(List SceneList) {
        try {
            // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 然后将得到的字符数据装载到ObjectOutputStream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
            objectOutputStream.writeObject(SceneList);
            // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
            String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            // 关闭objectOutputStream
            objectOutputStream.close();

            return SceneListString;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List String2SceneList(String SceneListString){
        try {
            byte[] mobileBytes = Base64.decode(SceneListString.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List SceneList = (List) objectInputStream.readObject();
            objectInputStream.close();
            return SceneList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Ctrl shift U 大小写转换
    public static String DEVICETYPE_CONSTANT ="devicetype_constant";
    public static String MAIN_IPADRESS = "main_ipadress";
    public static String LOCAL_CALL_DEVICE = "local_call_device";
    public static String SOCKET_PORT_NUM = "socket_port_num";


}
