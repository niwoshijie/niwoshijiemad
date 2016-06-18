package mainpagers.cpackage.serverScoket.udpsocket;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import base.APP;
import utils.SpUtils;
import utils.TextToSpeechAll;

/**
 * Created by LiuShao on 2016/2/21.
 */
public class LocalNetThread implements Runnable{
    private static LocalNetThread clientprotinstance;

    public static LocalNetThread newInstance(){
        if(clientprotinstance == null){
            clientprotinstance = new LocalNetThread();
            String localdevices = SpUtils.getString(APP.mAppApplication,SpUtils.LOCAL_CALL_DEVICE,"");
            if(!TextUtils.isEmpty(localdevices)){
                deviceList = SpUtils.String2SceneList(localdevices);
            }else{
                deviceList = new ArrayList<>();
            }
        }
        return clientprotinstance;
    }

    private boolean isMianServer;

    private static List<DeviceBean> deviceList ;

    public void setIsMianServer(boolean isMianServer) {
        this.isMianServer = isMianServer;
    }

    private String ipName;//本机被设置的叫号要用的信息
    private String localIpAdress;//本机Ip地址

    public void setIpName(String ipName,String localIpAdress){
        this.ipName = ipName;
        this.localIpAdress = localIpAdress;
    }

    private Thread udpThread = null;//接收UDP数据线程

    /**
     * 监听接收数据报信息
     */
    @Override
    public void run() {

        while(onWork) {

            try {
                udpSocket.receive(udpResPacket);
            } catch (IOException e) {
                onWork = false;

                if (udpResPacket != null) {
                    udpResPacket = null;
                }

                if (udpSocket != null) {
                    udpSocket.close();
                    udpSocket = null;
                }

                udpThread = null;

                Log.e(TAG, "UDP数据包接收失败！线程停止");
                break;
            }

            if (udpResPacket.getLength() == 0) {
                Log.i(TAG, "无法接收UDP数据或者接收到的UDP数据为空");
                continue;
            }


            String ipmsgStr = "";
            try {
                ipmsgStr = new String(resBuffer, 0, udpResPacket.getLength(),"gbk");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                Log.e(TAG, "接收数据时，系统不支持GBK编码");
            }//截取收到的数据

            Log.i(TAG, "接收到的UDP数据内容为:" + ipmsgStr);

            if(onreceivedMsg!=null){
                onreceivedMsg.onrecedMsg(ipmsgStr);
                TextToSpeechAll.getTextToSpeechAll().speakVoice(ipmsgStr);
            }

            String senderIp = udpResPacket.getAddress().getHostAddress();	//得到发送者IP

            int  aaa = ipmsgStr.indexOf("来");
            int bbb = ipmsgStr.indexOf("窗口");
            String deviceName = ipmsgStr.substring(aaa + 1, bbb);

            if (isMianServer && senderIp == localIpAdress){
                if(deviceList.size() == 0){
                    deviceList.add(new DeviceBean(deviceName,senderIp));
                    SpUtils.SceneList2String(deviceList);
                    SpUtils.saveString(APP.mAppApplication,SpUtils.LOCAL_CALL_DEVICE,"");
                }else{
                    for(int i=0;i<deviceList.size();i++){
                        if(!deviceList.get(i).getIpAdress().equals(senderIp)){
                            if(i == deviceList.size()-1){
                                deviceList.add(new DeviceBean(deviceName,senderIp));
                                SpUtils.SceneList2String(deviceList);
                                SpUtils.saveString(APP.mAppApplication, SpUtils.LOCAL_CALL_DEVICE, "");
                            }
                        }
                    }
                }

                for(int i = 0;i<deviceList.size();i++){
                    try {
                        sendData(ipmsgStr,InetAddress.getByName(deviceList.get(i).getIpAdress()));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            //得到发送者的ip地址
            Log.i(TAG,"接收到的IP地址是："+senderIp);
        }
    }

    private boolean onWork = false;	//线程工作标识

    private byte[] sendBuffer = null;
    private DatagramPacket udpSendPacket = null;	//用于发送的udp数据包
    private DatagramSocket udpSocket = null;	//用于接收和发送udp数据的socket
    public static final String TAG = "clientport";

    private ServerSocket serverSocket;

    /**
     * 发送数据包
     * @param sendStr
     * @param sendto
     */
    public synchronized void sendData(final String sendStr,final InetAddress sendto){

        try {
            sendBuffer = sendStr.getBytes("gbk");
            // 构造发送的UDP数据包
            udpSendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, sendto, ServerConstant.PORT);
            udpSocket.send(udpSendPacket);	//发送udp数据包
            Log.i(TAG, "成功向IP为" + sendto.getHostAddress() + "发送UDP数据：" + sendStr);
            udpSendPacket = null;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendToAll(String sendStr){
        for(int i = 0;i<deviceList.size();i++){
            try {
                sendData(sendStr,InetAddress.getByName(deviceList.get(i).getIpAdress()));
            } catch (UnknownHostException e) {
                e.printStackTrace();

            }
        }
    }

    private static final int BUFFERLENGTH = 1024; //缓冲大小
    private byte[] resBuffer = new byte[BUFFERLENGTH];	//接收数据的缓存

    private DatagramPacket udpResPacket = null;	//用于接收的udp数据包

    public boolean connectSocket(){	//监听端口，接收UDP数据
        boolean result = false;

        try {
            if(udpSocket == null){
                udpSocket = new DatagramSocket(ServerConstant.PORT);	//绑定端口
                Log.i(TAG, "connectSocket()....绑定UDP端口" + ServerConstant.PORT + "成功");
            }

            if(udpResPacket == null)
                udpResPacket = new DatagramPacket(resBuffer, BUFFERLENGTH);
            onWork = true;  //设置标识为线程工作
            startThread();	//启动线程接收udp数据
            result = true;
        } catch (SocketException e) {
            e.printStackTrace();
            disconnectSocket();
            Log.e(TAG, "connectSocket()....绑定UDP端口" + ServerConstant.PORT + "失败");
        }
        return result;
    }

    public void disconnectSocket(){	// 停止监听UDP数据
        onWork = false;	// 设置线程运行标识为不运行
        stopThread();
    }

    private void stopThread() {	//停止线程

        if(udpThread != null){
            udpThread.interrupt();	//若线程堵塞，则中断
        }
        Log.i(TAG, "停止监听UDP数据");
    }

    private void startThread() {	//启动线程
        if(udpThread == null){
            udpThread = new Thread(this);
            udpThread.start();
            Log.i(TAG, "正在监听UDP数据");
        }
    }

    public interface OnreceivedMsg{
        void onrecedMsg(String msg);
    }
    public OnreceivedMsg onreceivedMsg;
    public void setOnreceivedMsg(OnreceivedMsg onreceivedMsg) {
        this.onreceivedMsg = onreceivedMsg;
    }
}
