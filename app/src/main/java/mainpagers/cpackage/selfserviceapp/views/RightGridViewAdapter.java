package mainpagers.cpackage.selfserviceapp.views;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.beans.RightGoodsBean;

public class RightGridViewAdapter extends RecyclerView.Adapter<RightGridViewAdapter.RightHolder>{
    private LayoutInflater mLayoutInflater;
    private Context context;
    public RightGridViewAdapter(Context context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickLinster{
        void onItemClick(View view, int position);
    }
    private OnItemClickLinster onItemClickLinster;
    public void setOnItemClickLinster(OnItemClickLinster onItemClickLinster) {
        this.onItemClickLinster = onItemClickLinster;
    }

    private List<RightGoodsBean> goodDetials;
    public void setRightList(List<RightGoodsBean> goodDetials){
        this.goodDetials = goodDetials;
    }

    @Override
    public RightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.right_view_item,parent,false);
        RightHolder viewHolder = new RightHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RightHolder holder, int position) {
        holder.setData(position);
        if(onItemClickLinster!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickLinster.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return goodDetials.size();
    }

    class RightHolder extends RecyclerView.ViewHolder {
        ImageView goodsImage ;
        ImageView goodsDescribe;
        TextView goodsDetial ;
        TextView goodPrice;
        public RightHolder(View itemView) {
            super(itemView);
            goodsImage = (ImageView) itemView.findViewById(R.id.right_view_item_image);
            goodsDescribe = (ImageView) itemView.findViewById(R.id.right_view_xinpin);
            goodsDetial = (TextView) itemView.findViewById(R.id.right_view_name);
            goodPrice = (TextView) itemView.findViewById(R.id.right_view_money);
        }
        public void setData(int position) {
            goodsDetial.setText(goodDetials.get(position).goodsName);
            goodPrice.setText(goodDetials.get(position).goodsTitle);
        }
    }

}
