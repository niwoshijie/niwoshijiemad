package mainpagers.cpackage.selfserviceapp.ybselfservice;

import android.os.Environment;


public class FileConstants {

    private static final String RESOURSE_MENU = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String CACHE_BASE_PATH = RESOURSE_MENU+ "/hsdservice/";
    public static String IMAGE_CACHE_PATH = CACHE_BASE_PATH + "resource/";// 资源存储目录
    public static String PROPERTY_CACHE_PATH = CACHE_BASE_PATH + "property/";// 参数缓存存储目录


}
