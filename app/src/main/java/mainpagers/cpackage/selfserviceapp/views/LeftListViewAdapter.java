package mainpagers.cpackage.selfserviceapp.views;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.beans.LeftGoodsBean;

public class LeftListViewAdapter extends BaseAdapter {

    private List<LeftGoodsBean> goodsBeans;
    private LayoutInflater inflater;

    private int currentPosition = -1;
    public  void setPosition (int position){
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    public LeftListViewAdapter(List<LeftGoodsBean> goodsBeans,Activity act){
        this.goodsBeans = goodsBeans;
        inflater = act.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return goodsBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsViewholder goodsViewholder;
        if(convertView==null){
            goodsViewholder = new GoodsViewholder();
            convertView = inflater.inflate(R.layout.left_view_item, null);
            goodsViewholder.left_view_image = (ImageView) convertView.findViewById(R.id.left_view_image);
            goodsViewholder.left_view_text = (TextView) convertView.findViewById(R.id.left_view_text);
            goodsViewholder.ll_left_view = (LinearLayout) convertView.findViewById(R.id.ll_left_view);
            convertView.setTag(goodsViewholder);
        }else{
            goodsViewholder = (GoodsViewholder) convertView.getTag();
        }

//      goodsViewholder.left_view_image.setImageResource(R.mipmap.ic_launcher);
        goodsViewholder.left_view_text.setText(goodsBeans.get(position).textString);
        if(position == currentPosition){
            goodsViewholder.ll_left_view.setBackgroundResource(R.mipmap.left_selected_back);
        }else{
            goodsViewholder.ll_left_view.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    class GoodsViewholder {
        private ImageView left_view_image;
        private TextView left_view_text;
        private LinearLayout ll_left_view;
    }

}
