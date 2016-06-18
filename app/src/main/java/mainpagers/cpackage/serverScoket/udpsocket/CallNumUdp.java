package mainpagers.cpackage.serverScoket.udpsocket;

import android.content.Context;

/**
 * Created by LiuShao on 2016/3/4.
 */
public class CallNumUdp {

    private static CallNumUdp callNumUdp;
    private Context context;
    public CallNumUdp(Context context) {
        this.context = context;
    }
    public static CallNumUdp getInstance(Context context){
        if(callNumUdp ==null){
            callNumUdp = new CallNumUdp(context);
        }
        return callNumUdp;
    }



}
