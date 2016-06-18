package utils;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuShao on 2016/4/6.
 */
public class HandTextUtils {

    /**
     * 字符串转成int
     * @param str
     * @return
     */
    public static int parseStr2Int(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 字符串转成float
     * @param str
     * @return
     */
    public static float parseStr2Float(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 判断字符串是否是合法进制
     * @param str
     * @return
     * @return: boolean
     * @date: 2014-1-21 上午10:13:23
     */
    public static boolean isHexString(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.matches("^[0-9a-fA-F]++$", str);
    }

    /**
     * 字符串转成Long
     * @param str
     * @return
     */
    public static long parseStr2Long(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }


    /**
     * 计算字符个数，一个汉字算两个
     * @param s
     * @return
     */
    public static int countWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length(), a = 0, b = 0;
        int len = 0;
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (Character.isSpaceChar(c)) {
                ++b;
            }
            else if (isAscii(c)) {
                ++a;
            }
            else {
                ++len;
            }
        }
        return len + (int) Math.ceil((a + b) / 2.0);
    }

    public static boolean isAscii(char c) {
        return c <= 0x7f;
    }

    /**
     * 验证手机号格式是否正确
     * @param mobileNumber
     * @return
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", mobileNumber)) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符是否适合某种格式
     * @param expression 正则表达
     * @param text 操作的字符串
     * @return
     */
    private static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression); // 正则表达�?
        Matcher m = p.matcher(text); // 操作的字符串
        boolean b = m.matches();
        return b;
    }


    /**
     * 获取链接的文件名
     * @param fileUrl
     * @return
     */
    public static String getFileName(String fileUrl){
        String fileName = "";
        if(!TextUtils.isEmpty(fileUrl)){
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length());
        }
        return fileName;
    }


    public static String parserInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bys = new byte[1024];
            int len = 0;
            while((len = is.read(bys)) != -1) {
                baos.write(bys,0,len);
            }
            String result = new String(baos.toByteArray());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
