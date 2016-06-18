package mainpagers.cpackage.selfserviceapp.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import liushaobo.mad.R;
import mainpagers.cpackage.selfserviceapp.beans.BottomItemBean;


public class BottomViewAdapter extends RecyclerView.Adapter<BottomViewAdapter.BottomHolder> {
    private Context context;

    public BottomViewAdapter(Context context) {
        this.context = context;
    }

    private List<BottomItemBean> bottomItemBeans;

    public void setData(List<BottomItemBean> bottomItemBeans) {
        this.bottomItemBeans = bottomItemBeans;
    }


    public interface OnItemClickLinster {
        void onItemClick(View view, int position);
    }

    private OnItemClickLinster onItemClickLinster;
    public void setOnItemClickLinster(OnItemClickLinster onItemClickLinster) {
        this.onItemClickLinster = onItemClickLinster;
    }

    @Override
    public BottomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_view_item, parent, false);
        return new BottomHolder(view);
    }

    @Override
    public void onBindViewHolder(final BottomHolder holder, final int position) {
        holder.setData(position);
        if (onItemClickLinster != null) {
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
        return bottomItemBeans.size();
    }

    class BottomHolder extends RecyclerView.ViewHolder {
        TextView bottom_goods_name;
        TextView bottom_goods_count;
        TextView bottom_goods_money;

        public BottomHolder(View itemView) {
            super(itemView);
            bottom_goods_name = (TextView) itemView.findViewById(R.id.bottom_goods_name);
            bottom_goods_count = (TextView) itemView.findViewById(R.id.bottom_goods_count);
            bottom_goods_money = (TextView) itemView.findViewById(R.id.bottom_goods_money);
        }

        public void setData(int position) {
            bottom_goods_name.setText(bottomItemBeans.get(position).bottmItemName);
            bottom_goods_count.setText(bottomItemBeans.get(position).bottmItemCount);
            bottom_goods_money.setText(bottomItemBeans.get(position).bottmItemName);
        }
    }

    /**
     * 增加item
     */
    public void addData(){
        bottomItemBeans.add(null);
        notifyItemInserted(bottomItemBeans.size());
    }

    /**
     * 删除item
     */
    public void deleteData(int position){
        bottomItemBeans.remove(position);
        notifyItemRemoved(position);
    }

}


