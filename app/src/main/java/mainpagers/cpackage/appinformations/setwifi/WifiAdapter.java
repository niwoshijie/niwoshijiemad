package mainpagers.cpackage.appinformations.setwifi;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import base.APP;
import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/3/17.
 */
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiHolder>{

    private List<XmlBean> wifiList;

    public void setWifiList(List<XmlBean> wifiLists){
        this.wifiList = wifiLists;
    }

    @Override
    public WifiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(APP.mAppApplication, R.layout.wifi_view_item,null);
        return new WifiHolder(view);
    }

    @Override
    public void onBindViewHolder(WifiHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    class WifiHolder extends RecyclerView.ViewHolder{
        TextView tv_wifi_accout;
        TextView tv_wifi_secret;
        TextView wifi_scan_result;
        View view;
        ProgressBar progressBar;
        public WifiHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tv_wifi_accout = (TextView) itemView.findViewById(R.id.tv_wifi_accout);
            tv_wifi_secret = (TextView) itemView.findViewById(R.id.tv_wifi_secret);
            wifi_scan_result = (TextView) itemView.findViewById(R.id.wifi_scan_result);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pro_gressbar);
        }

        public void setData(int position) {

            tv_wifi_accout.setText(wifiList.get(position).getWifi() + "");
            tv_wifi_secret.setText(wifiList.get(position).getPwd()+"");
            wifi_scan_result.setText(wifiList.get(position).getResult()+"");

            if(wifiList.get(position).isScaning()){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.INVISIBLE);
            }

            if(wifiList.get(position).getCurrentStatus()==0){

            }else if(wifiList.get(position).getCurrentStatus()==1){
                view.setBackgroundColor(Color.parseColor("#2baf2b"));
            }else{
                view.setBackgroundColor(Color.parseColor("#d01716"));
            }
        }
    }

        public void notifyDataItem(int position){
            notifyItemChanged(position);
        }

}
