package utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by LiuShao on 2016/5/21.
 */
public class ImageUtils {
    /**
     * @param drawable
     * drawable 转  Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawableToBitmap1(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);
        return bitmap;

    }


    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options,480,800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth){
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 解码图片
     * @param url：路径
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片到SD卡
     * @param saveBitmap：需要保存的图片
     * @param bitmapSavePath：图片保存的路径
     */
    public static void saveBitmapToSDCard(Bitmap saveBitmap, String bitmapSavePath) {
        if (checkSDCardAvailable()) {//检查是否存在SD卡
            File bitmapFile = new File(bitmapSavePath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(bitmapFile);
                if (saveBitmap != null) {
                    if (saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)) {//压缩
                        fos.flush();
                    }
                }
            } catch (Exception e) {
                bitmapFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检查是否存在SD卡
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 根据路径加载Bitmap
     * @param bitmapPath：图片路径
     * @param bitmapWidth：图片宽
     * @param bitmapHeight：图片高
     * @return Bitmap
     */
    public static Bitmap convertToBitmap(String bitmapPath, int bitmapWidth, int bitmapHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //设置为ture只获取图片大小
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            //返回为空
            BitmapFactory.decodeFile(bitmapPath, options);
            int width = options.outWidth;
            int height = options.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > bitmapWidth || height > bitmapHeight) {
                //缩放图片
                scaleWidth = ((float) width) / bitmapWidth;
                scaleHeight = ((float) height) / bitmapHeight;
            }
            options.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            options.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<>(BitmapFactory.decodeFile(bitmapPath, options));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }







    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(data!=null){
            return Base64.encodeToString(data,Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
        }
        return null;
    }


    public String bitmaptoString(Bitmap bitmap){
        //将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string = Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }


}
