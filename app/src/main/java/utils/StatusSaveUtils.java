package utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by LiuShao on 2016/2/21.
 * 保存但前的存储类型的工具类
 */
public class StatusSaveUtils {

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


}
