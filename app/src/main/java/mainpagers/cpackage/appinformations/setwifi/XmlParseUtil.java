package mainpagers.cpackage.appinformations.setwifi;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析wifi.xml文件的工具类
 */
public class XmlParseUtil {

    private static XmlParseUtil xmlParseUtil;

    public static XmlParseUtil getXmlParseUtil() {
        if (xmlParseUtil == null) {
            xmlParseUtil = new XmlParseUtil();
        }
        return xmlParseUtil;
    }


    @SuppressWarnings("static-access")
    public  List<XmlBean> parseXml(InputStream is) throws XmlPullParserException {

        List<XmlBean> xmlBeanList = null;
        XmlBean xmlBean = null;
        try {

            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(is, "utf-8");
            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        xmlBeanList = new ArrayList<XmlBean>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("item")) {
                            xmlBean = new XmlBean();
                        } else if (xmlPullParser.getName().equals("wifi")) {
                            eventType = xmlPullParser.next();
                            xmlBean.setWifi(xmlPullParser.getText());
                        } else if (xmlPullParser.getName().equals("pwd")) {
                            eventType = xmlPullParser.next();
                            xmlBean.setPwd(xmlPullParser.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("item")) {
                            xmlBean.setResult("");
                            xmlBean.setIsScaning(false);
                            xmlBean.setCurrentStatus(0);
                            xmlBeanList.add(xmlBean);
                            xmlBean = null;
                        }
                        break;
                    default:
                        break;
                }
                try {
                    eventType = xmlPullParser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlBeanList;
    }


    public  List<XmlBean> getItem() {
        List<XmlBean> xmlList = null;
        try {
            String pathString = "";
            File sdFile = new File(UsbPath+"/ybwifi.xml");
            if (sdFile.exists()) {
                pathString = UsbPath+"/ybwifi.xml";
            }
            InputStream is = new FileInputStream(pathString);

            // InputStream is = getAssets().open("default_item.xml");
            XmlParseUtil xmlParseUtil = XmlParseUtil.getXmlParseUtil();

            try {
                xmlList = xmlParseUtil.parseXml(is);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlList;
    }


    public boolean fileIsExists(String path) {
        File f = new File(path + "/ybwifi.xml");
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    private String UsbPath;
    public void setWifiPath(String UsbPath) {
        this.UsbPath = UsbPath;
    }
}
