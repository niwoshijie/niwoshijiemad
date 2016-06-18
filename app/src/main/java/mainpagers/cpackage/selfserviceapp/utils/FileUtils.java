package mainpagers.cpackage.selfserviceapp.utils;

import java.io.File;

/**
 * Created by LiuShao on 2016/4/6.
 */
public class FileUtils {

    /**
     * 文件是否存在
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }


    /**
     * 创建目录
     * @param path
     */
    public static void createDirs(File path) {
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
    }





}
