package utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by LiuShao on 2016/6/14.
 */
public class permissionUtils {
    private Activity thisActivity;
    private void setPermission() {
        //判断是否有权限
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){}

//请求权限
//            ActivityCompat.requestPermissions(thisActivity,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//判断是否需要 向用户解释，为什么要申请该权限
//        ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,Manifest.permission.READ_CONTACTS);
//权限申请结果
//        onRequestPermissionsResult( int requestCode,String permissions[], int[] grantResults)
    }



}
