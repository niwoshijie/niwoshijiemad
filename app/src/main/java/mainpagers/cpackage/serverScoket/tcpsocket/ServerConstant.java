package mainpagers.cpackage.serverScoket.tcpsocket;

import android.text.TextUtils;

/**
 * Created by LiuShao on 2016/2/22.
 */
public class ServerConstant {

    public static int PORT = 10000;//设置默认端口号10000

    static{
        String portNum = CallNumCache.getSocketPortNum();
        if(!TextUtils.isEmpty(portNum)){
            PORT = Integer.valueOf(portNum);
        }
    }


}
