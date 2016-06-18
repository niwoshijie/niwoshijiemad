package mainpagers.cpackage.serverScoket.tcpsocket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by LiuShao on 2016/3/1.
 */
public class ClientSide {

    private boolean isConnected = false;
    private Socket socketClient = null;
    private PrintWriter printWriterClient = null;
    private BufferedReader bufferedReaderClient ;
    private String serveripAdress;

    private Thread checkConnectionThred = null;
    private TcpClientThread clientThread = null;
    public static final String TAG = "clientport";

    private static ClientSide clientSide;

    private ClientSide() {}


    public static ClientSide getInstance() {
        if (clientSide == null) {
            clientSide = new ClientSide();

        }
        return clientSide;
    }

    public void setServerIpAddress(String serveripAdress) {
        isConnected = false;
        this.serveripAdress = serveripAdress;
        if (clientThread == null) {
            Log.e(TAG, "启动客户端线程！！！！");
            clientThread = new  TcpClientThread();
            clientThread.start();
        } else {
            //重新设置
            clientThread.initTcpSocket();
        }
    }







    /**
     * 向服务器发送消息
     *
     * @param message
     */
    public  void sendData(String message) {
        if (isConnected ) {
            Log.e(TAG, "客户端发送socketIP地址和连接状态" + socketClient.getInetAddress() + "  " + socketClient.isConnected() + "   " + socketClient.isClosed());
            try {
                printWriterClient.print(message);
                printWriterClient.flush();
                Log.i(TAG, "客户端发送信息:" + message);
            } catch (Exception e) {
                Log.i(TAG, "发送信息异常:" + e.getMessage());
            }
        } else {

            if (conFaile != null) {
                conFaile.received(message);
            }
        }
    }

    private class ClientHeartThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                    socketClient.sendUrgentData(0xFF);
                    isConnected = true;
                } catch (Exception e) {
                    isConnected = false;
                    try {
                        socketClient = new Socket(serveripAdress, ServerConstant.PORT);
                        bufferedReaderClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                        printWriterClient = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream()), true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                Log.i(TAG, "isConnected:" + isConnected);
            }
        }
    }

    //TCP客户端线程
    private class TcpClientThread extends Thread {
        public void initTcpSocket() {
            try {
                Log.i(TAG, "启动客户端！！！！！:");
                //连接服务器
                socketClient = new Socket(serveripAdress, ServerConstant.PORT);
                //取得输入、输出流
                bufferedReaderClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream(), "utf-8"));
                printWriterClient = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream()), true);
                isConnected = true;
                Log.i(TAG, "连接服务器成功:");
                //启动线程监控
            } catch (Exception e) {
                Log.i(TAG, "连接服务器失败:" + e.getMessage());
            }
            runCheckThread();
        }

        public void run() {
            initTcpSocket();
            char[] buffer = new char[256];
            try {
                int length = 0;
                while (true) {

                    try{
                        if (bufferedReaderClient != null) {
                            if ((length = bufferedReaderClient.read(buffer)) != -1) {
                                String receiveMsg = getInfoBuff(buffer, length);
                                Log.e(TAG, "客户端接收消息:" + receiveMsg);
                                if (onreceivedMsg != null) {
                                    onreceivedMsg.onrecedMsg(receiveMsg);
                                }
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                Log.e(TAG, "错误信息！！！！！" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private String getInfoBuff(char[] buff, int count) {
        char[] temp = new char[count];
        for (int i = 0; i < count; i++) {
            temp[i] = buff[i];
        }
        return new String(temp);
    }
    private void runCheckThread(){
        //启动检测线程
        if (checkConnectionThred == null) {
            checkConnectionThred = new ClientHeartThread();
            checkConnectionThred.start();
        }
    }


    public interface OnreceivedMsg {
        void onrecedMsg(String msg);
    }
    public OnreceivedMsg onreceivedMsg;
    public void setOnreceivedMsg(OnreceivedMsg onreceivedMsg) {
        this.onreceivedMsg = onreceivedMsg;
    }


    public interface ConFaile {
        void received(String msgs);
    }
    public ConFaile conFaile;
    public void setConFaile(ConFaile conFaile) {
        this.conFaile = conFaile;
    }


    /**
     * 断开连接
     */
    public void disconnectSocket() {
        if (isConnected) {
            try {
                if (socketClient != null) {
                    socketClient.close();
                    socketClient = null;
                }
                if (printWriterClient != null) {
                    printWriterClient.close();
                    printWriterClient= null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            isConnected = false;
        }
    }
}
