package mainpagers.cpackage.selfserviceapp.utils.caches;


import java.io.File;

import mainpagers.cpackage.selfserviceapp.ybselfservice.FileConstants;

/**
 * Created by LiuShao on 2016/4/11.
 */
public class CacheUtils {
    private static final String CACHE_SAVE_PATH = FileConstants.PROPERTY_CACHE_PATH;

    private static ACache acache = ACache.get(new File(CACHE_SAVE_PATH));

    //设备编号
    private static final String MAC_INFO_CONSTANT = "MAC";


    public  static void setDeviceNo(String mac) {
        acache.put(MAC_INFO_CONSTANT, mac);
    }
    public  static String getDeviceNo() {
        return acache.getAsString(MAC_INFO_CONSTANT);
    }


}
