package mainpagers.cpackage.serverScoket.tcpsocket;

import android.util.Log;

/**
 * Created by LiuShao on 2016/2/26.
 */
public class SocketControl {

    private static SocketControl socketControl;
    private String serverIpAddress;
    private boolean isMainServer;
    public static final String TAG = "clientport";
    private SocketControl(){

    }

    public static SocketControl getInstance() {
        if (socketControl == null) {
            socketControl = new SocketControl();
        }
        return socketControl;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public void setIsMainServer(boolean isMainServer) {
        this.isMainServer = isMainServer;
    }

    /**
     * 初始化
     */
    public void connectSocket() {
        Log.i(TAG, "初始化connectSocket:" + isMainServer);
        if (isMainServer) {
            ServerSide.getInstance().startServerThread();
        }

        ClientSide.getInstance().setServerIpAddress(serverIpAddress);
    }

    public void disconnectSocket() {
        Log.i(TAG, "disconnectSocket:" + isMainServer);
        if (isMainServer) {
            //断开服务器连接
            ServerSide.getInstance().disconnectSocket();
        }
        //断开客户端连接
        ClientSide.getInstance().disconnectSocket();
    }

    public void sendData(String message) {
        ClientSide.getInstance().sendData(message);
    }
}
