package mainpagers.cpackage.stytlespackage;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import liushaobo.mad.R;

/**
 * Created by feige on 2016/8/17.
 */
public class WifiListAdapter extends MyPagerAdapter<ScanResult> {

    public WifiListAdapter(List<ScanResult> dataList, Context context) {
        super(dataList, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiListHolder wifiListHolder;
        if(convertView == null){
            convertView =  inflate.inflate(R.layout.wifi_list_style,null);
            wifiListHolder = new WifiListHolder();
            wifiListHolder.tv_wifi_list= (TextView) convertView.findViewById(R.id.tv_wifi_list);
            convertView.setTag(wifiListHolder);
        }else {
            wifiListHolder = (WifiListHolder) convertView.getTag();
        }

        wifiListHolder.tv_wifi_list.setText(dataList.get(position).SSID);
        return convertView;
    }

    private class WifiListHolder {
        TextView tv_wifi_list;
    }

}
