package mainpagers.cpackage.serverScoket.udpsocket;

/**
 * Created by LiuShao on 2016/2/22.
 */
public class DeviceBean {
    private String ipName;
    private  String ipAdress;

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public DeviceBean(String ipName, String ipAdress) {
        this.ipName = ipName;
        this.ipAdress = ipAdress;
    }

    public void setIpAdress(String ipAdress) {


        this.ipAdress = ipAdress;
    }
}
