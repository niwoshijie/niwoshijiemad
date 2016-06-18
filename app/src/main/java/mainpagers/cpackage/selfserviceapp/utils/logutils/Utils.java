package mainpagers.cpackage.selfserviceapp.utils.logutils;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {





    /**
     * 验证邮箱地址是否合法
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }
        catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 过滤文本中的html脚本信息
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        }
        catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符�?
    }

    /**
     * 写图片到SD卡
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e) {
            LogUtils.printStackTrace(e);
        }
        catch (IOException e) {
            LogUtils.printStackTrace(e);
        }
    }

    /**
     * 从网络下载图片并保存到指定路径
     * @param imgUrl
     * @param filePath
     */
    public static void downloadImageAndSave(String imgUrl, String filePath) {
        URL url;
        InputStream is = null;
        FileOutputStream fos = null;
        URLConnection conn;
        try {
            url = new URL(imgUrl);
            conn = url.openConnection();
            is = conn.getInputStream();
            fos = new FileOutputStream(new File(filePath));
            Utils.copyStream(is, fos);
        }
        catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        finally {
            try {
                is.close();
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝
     * @param is
     * @param os
     */
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        if (is == null || os == null) {
            return;
        }
        BufferedInputStream bufIs;
        boolean shouldClose = false;
        if (is instanceof BufferedInputStream) {
            bufIs = (BufferedInputStream) is;
        }
        else {
            bufIs = new BufferedInputStream(is);
            shouldClose = true;
        }

        int bufLen = 102400;
        byte[] buf = new byte[bufLen];
        int len;
        while (true) {
            len = bufIs.read(buf);
            if (len < 0) {
                break;
            }
            os.write(buf, 0, len);
        }
        if (shouldClose) {
            bufIs.close();
        }
    }



    public static int calculateCharLength(String src) {
        int counter = -1;
        if (src != null) {
            counter = 0;
            final int len = src.length();
            for (int i = 0; i < len; i++) {
                char sigleItem = src.charAt(i);
                if (isAlphanumeric(sigleItem)) {
                    counter++;
                }
                else if (Character.isLetter(sigleItem)) {
                    counter = counter + 2;
                }
                else {
                    counter++;
                }
            }
        }
        else {
            counter = -1;
        }

        return counter;
    }

    /**
     * 判断字符是否为英文字母或者阿拉伯数字.
     * @param ch char字符
     * @return true or false
     */
    public static boolean isAlphanumeric(char ch) {
        // 常量定义
        final int DIGITAL_ZERO = 0;
        final int DIGITAL_NINE = 9;
        final char MIN_LOWERCASE = 'a';
        final char MAX_LOWERCASE = 'z';
        final char MIN_UPPERCASE = 'A';
        final char MAX_UPPERCASE = 'Z';

        if ((ch >= DIGITAL_ZERO && ch <= DIGITAL_NINE) || (ch >= MIN_LOWERCASE && ch <= MAX_LOWERCASE)
                || (ch >= MIN_UPPERCASE && ch <= MAX_UPPERCASE)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * decode js用escape编码的字符串
     * @method: unEscape
     * @author: DongFuhai
     * @param src
     * @return
     * @return: String
     * @date: 2013-10-14 下午5:57:56
     */
    public static String unEscape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                }
                else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            }
            else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                }
                else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
}
