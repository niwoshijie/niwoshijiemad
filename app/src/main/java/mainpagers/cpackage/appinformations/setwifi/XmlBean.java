package mainpagers.cpackage.appinformations.setwifi;

/**
 * Created by LiuShao on 2016/3/7.
 */
public class XmlBean {
    private String wifi;
    private String pwd;

    private boolean isScaning;
    private String result;
    private int currentStatus;//0,1,2 未扫描，已扫描成功，已扫描失败
    public int getCurrentStatus() {
        return currentStatus;
    }
    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public boolean isScaning() {
        return isScaning;
    }

    public void setIsScaning(boolean isScaning) {
        this.isScaning = isScaning;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWifi() {
        return wifi;
    }
    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public XmlBean() {
    }
}
