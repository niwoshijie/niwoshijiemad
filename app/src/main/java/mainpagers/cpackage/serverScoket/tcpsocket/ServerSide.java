package mainpagers.cpackage.serverScoket.tcpsocket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LiuShao on 2016/3/1.
 */
public class ServerSide {

    private Socket socket = null;
    private ServerSocket socketServer = null;

    public static final String TAG = "clientport";

    //存储所有的客户端集合
    private static List<Socket> devicelist;
    //单例服务端
    private static ServerSide serverSide;

    private TcpServerThread tcpServerThread = null;

    private ServerSide() {
    }

    //
    public static ServerSide getInstance() {
        if (serverSide == null) {
            serverSide = new ServerSide();
        }
        return serverSide;
    }

    public void startServerThread() {
        //让上一次的线程退出
        //获取之前链接过的
        devicelist = new ArrayList<Socket>();
        //重启启动线程，保证每次配置完整
        if (tcpServerThread == null) {
            tcpServerThread = new TcpServerThread();
            tcpServerThread.start();
        } else {
            tcpServerThread.initServerSocket();
        }
    }

    /**
     * 服务端发送信息给客户端
     */
    public void sendToAll(String message) {
        if (message.length() < 3) {
            return;
        }
        if (socketServer != null) {
            int num = devicelist.size();
            for (int i = 0; i < num; i++) {
                Socket mSocket = devicelist.get(i);
                Log.i(TAG, "服务器发送消息:" + message + "  客户端接收ip:" + mSocket.getInetAddress() + " 连接状态:" + mSocket.isConnected());
                if (mSocket.isConnected()) {
                    try {
                        PrintWriter printWriterServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
                        printWriterServer.print(message);
                        printWriterServer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //TCP服务端线程
    private class TcpServerThread extends Thread {

        private ExecutorService mExecutorService = Executors.newCachedThreadPool(); //thread pool

        public void initServerSocket() {
            try {
                socketServer = new ServerSocket(ServerConstant.PORT);
            } catch (IOException e) {

            }
        }

        public void run() {
            Log.i(TAG, "启动服务端！！！！！！！！！！！！！");
            initServerSocket();
            while (true) {
                try {
                    socket = socketServer.accept();
                    //将连接的设备存起来
                    for (int i = 0; i < devicelist.size(); i++) {
                        Socket socket = devicelist.get(i);
                        if (!socket.isConnected() || socket.isClosed()) {
                            devicelist.remove(i);
                            i--;
                        }
                    }
                    devicelist.add(socket);

                    mExecutorService.execute(new ServerThread(socket));

                    String clientIP = socket.getInetAddress().toString();
                    clientIP = clientIP.substring(clientIP.lastIndexOf("/") + 1);
                    Log.i(TAG, "进来一个客户端:" + clientIP + "   设备集合大小:" + devicelist.size());
                } catch (IOException e) {
//                    Log.i(TAG, "连接客户端异常:" + e.getMessage());
                }
            }
        }
    }

    /**
     * 接收消息
     */
    private class ServerThread extends Thread {

        private Socket socket = null;
        private BufferedReader bufferedReaderServer = null;

        public ServerThread(Socket socket) {
            this.socket = socket;
            try {
                bufferedReaderServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            char[] buffer = new char[256];
            int count = 0;
            while (true) {
                try {
                    if ((count = bufferedReaderServer.read(buffer)) > 0) {
                        String receiveMsg = getInfoBuff(buffer, count);
                        Log.i(TAG, "服务器接收消息:" + receiveMsg);
                        sendToAll(receiveMsg);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "服务器接收消息失败:" + e.getMessage());
                    return;
                }
            }
        }
    }

    /**
     * 断开连接
     */
    public void disconnectSocket() {
        try {
            if (socketServer != null) {
                socketServer.close();
            }
            for (int i = 0; i < devicelist.size(); i++) {
                Socket socket = devicelist.get(i);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String getInfoBuff(char[] buff, int count) {
        char[] temp = new char[count];
        for (int i = 0; i < count; i++) {
            temp[i] = buff[i];
        }
        return new String(temp);
    }
}
