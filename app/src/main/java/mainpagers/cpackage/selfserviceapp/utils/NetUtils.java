package mainpagers.cpackage.selfserviceapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.lang.reflect.Type;
import java.util.Map;

import base.APP;
import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/4/6.
 */
public class NetUtils {

    /**
     * 网络状态
     */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //获取系统网络连接管理�?
        if (connectivity == null) { //如果网络管理器为null
            return false; //返回false表明网络无法连接
        }
        else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo(); //获取�?��的网络连接对�?
            if (info != null) { //网络信息不为null�?
                for (int i = 0; i < info.length; i++) { //遍历网路连接对象
                    if (info[i].isConnected()) { //当有�?��网络连接对象连接上网络时
                        return true; //返回true表明网络连接正常
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断手机网络是否可用
     * @param context
     * @return
     */
    public static boolean isMobileNetworkAvailable(Context context) {
        //获取应用上下�?
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取系统的连接服�?
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        //获取网络的连接情�?
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //判断3G�?
            return true;
        }
        return false;
    }

    //得到本地IP地址（WIFI）
    private String GetLocalIP() {
        WifiManager wifiManager = (WifiManager) APP.mAppApplication.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }


    /**
     * post请求
     * @param url 要访问的url
     * @param map 请求带的参数
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


    public static class JsonResponseParser implements ResponseParser {
        //检查服务器返回的响应头信息
        @Override
        public void checkResponse(UriRequest request) throws Throwable {}

        /**
         * 转换result为resultType类型的对象
         *
         * @param resultType  返回值类型(可能带有泛型信息)
         * @param resultClass 返回值类型
         * @param result      字符串数据
         * @return
         * @throws Throwable
         */
        @Override
        public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
            return new Gson().fromJson(result, resultClass);
        }
    }

    public static class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
        @Override
        public void onSuccess(ResultType result) {
        }
        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }
        @Override
        public void onCancelled(CancelledException cex) {
        }
        @Override
        public void onFinished() {
        }
    }

    /**
     * 设置xutils加载图片的配置
     * @return
     */
    public static ImageOptions setImageOption() {
        ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                        //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.ic_launcher)
                        //设置使用缓存
                .setUseMemCache(true)
                        //设置显示圆形图片
                .setCircular(false)
                        //设置支持gif
                .setIgnoreGif(false)
                .build();
        return options;
    }

    /**
     * 加载图片的工具类
     * @param imageview
     * @param url
     */
    public static void setImageResourse(ImageView imageview,String url){
      x.image().bind(imageview, url, setImageOption());
//    x.image().bind(imageView, "file:///sdcard/test.gif", imageOptions);
//    x.image().bind(imageView, "assets://test.gif", imageOptions);
//    x.image().bind(imageView, url, imageOptions, new Callback.CommonCallback<Drawable>() {...});
//    x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {...});
//    x.image().loadFile(url, imageOptions, new Callback.CommonCallback<File>() {...});
    }



}
