package utils;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LiuShao on 2016/5/21.
 */
public class NetUtils {

    private static final String TAG = "NetUtils";

    public static String simplePost(final String httpUrl, final Map<String, String> paramMap) {
        //获得的数据
        String resultData = "";
        URL url = null;
        try {
            //构造一个URL对象
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException");
        }
        if (url != null) {
            try {
                // 使用HttpURLConnection打开连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                //因为这个是post请求,设立需要设置为true
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                // 设置以POST方式
                urlConn.setRequestMethod("POST");
                // Post 请求不能使用缓存
                urlConn.setUseCaches(false);
                urlConn.setInstanceFollowRedirects(true);
                // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                // 要注意的是connection.getOutputStream会隐含的进行connect。
                urlConn.connect();
                //DataOutputStream流

                StringBuffer params = null;
                //要上传的参数
//                String content = "par=" + URLEncoder.encode("ABCDEFG", "gb2312");
                if (paramMap != null && paramMap.size() > 0) {
                    Iterator it = paramMap.entrySet().iterator();
                    params = new StringBuffer();
                    while (it.hasNext()) {
                        Map.Entry element = (Map.Entry) it.next();
                        params.append(element.getKey());
                        params.append("=");
                        params.append(element.getValue());
                        params.append("&");
                    }
                    if (params.length() > 0) {
                        params.deleteCharAt(params.length() - 1);
                    }

                    OutputStream os = null;
                    try {
                        os = urlConn.getOutputStream();
                        os.write(params.toString().getBytes());
                        os.flush();
                        os.write(params.toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());

                //将要上传的内容写入流中
//                out.writeBytes(content);
                out.writeBytes(params.toString());
                //刷新、关闭
                out.flush();
                out.close();
                //获取数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String inputLine = null;
                //使用循环来读取获得的数据
                while (((inputLine = reader.readLine()) != null)) {
                    //我们在每一行后面加上一个"\n"来换行
                    resultData += inputLine + "\n";
                }
                reader.close();
                //关闭http连接
                urlConn.disconnect();
                //设置显示取得的内容
                if (resultData != null) {
                    return resultData;
                } else {
                    return "";
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            }
        } else {
            Log.e(TAG, "Url NULL");
        }
        return "";
    }


    /**
     * 简单的获取数据
     *
     * @param path
     * @param paramMap
     * @return
     */
    public static String simplePostA(final String path, final Map<String, String> paramMap) {

        HandleMessageUtils.getInstance().runInThread(new Runnable() {
            @Override
            public void run() {

                String message = "";
                StringBuffer params = null;
                HttpURLConnection urlConn = null;

                try {
                    // 新建一个URL对象
                    URL url = new URL(path);
                    // 打开一个HttpURLConnection连接
                    urlConn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                urlConn.setRequestProperty("Connection", "keep-alive");

                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 设置请求的头
                urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

                // 设置请求的方式
                try {
                    urlConn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                // 设置请求的超时时间
                urlConn.setReadTimeout(5000);
                urlConn.setConnectTimeout(5000);


                if (paramMap != null && paramMap.size() > 0) {
                    Iterator it = paramMap.entrySet().iterator();
                    params = new StringBuffer();
                    while (it.hasNext()) {
                        Map.Entry element = (Map.Entry) it.next();
                        params.append(element.getKey());
                        params.append("=");
                        params.append(element.getValue());
                        params.append("&");
                    }
                    if (params.length() > 0) {
                        params.deleteCharAt(params.length() - 1);
                    }

                    OutputStream os = null;
                    try {
                        os = urlConn.getOutputStream();
                        os.write(params.toString().getBytes());
                        os.flush();
                        os.write(params.toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                // 发送POST请求必须设置如下两行
                urlConn.setDoOutput(true);
                urlConn.setDoInput(true);
                // Post请求不能使用缓存
                urlConn.setUseCaches(false);

                // 开始连接
                try {
                    urlConn.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 判断请求是否成功
                try {
                    if (urlConn.getResponseCode() == 200) {
                        InputStream is = urlConn.getInputStream();
                        message = HandTextUtils.parserInputStream(is);
                    } else {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return "";
    }


    /**
     * post请求
     *
     * @param url      要访问的url
     * @param map      请求带的参数
     * @param callback 回调
     * @param <T>
     * @return
     */
    public static <T> Callback.Cancelable Post(String url, Map<String, Object> map, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


}
