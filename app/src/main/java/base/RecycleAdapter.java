package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import liushaobo.mad.R;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.AppHolder>{

    private Context context;

    private String[] itemName;
    private int[] itemImage;
    public void setRecorse(String[] itemName,int[] itemImage){
        this.itemImage = itemImage;
        this.itemName = itemName;
    }

    public interface OnItemClickLinster{
        void onItemClick(View view, int position);
//      void onItemLongClick(View view, int position);
    }

    private OnItemClickLinster onItemClickLinster;

    public void setOnItemClickLinster(OnItemClickLinster onItemClickLinster) {
        this.onItemClickLinster = onItemClickLinster;
    }

    public RecycleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppHolder holder,final int position) {
        holder.setData(position);
        if(onItemClickLinster!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickLinster.onItemClick(holder.itemView, pos);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = holder.getLayoutPosition();
//                    onItemClickLinster.onItemLongClick(holder.itemView, pos);
//                    return false;
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return itemName.length;
    }

    class AppHolder extends RecyclerView.ViewHolder{
            ImageView item_image;
            TextView tv_text_view;

        public AppHolder(View itemView) {
            super(itemView);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            tv_text_view = (TextView) itemView.findViewById(R.id.tv_text_view);
        }

        public void setData(int position) {
            item_image.setImageResource(itemImage[position]);
            tv_text_view.setText(itemName[position]);
        }
    }

    /**
     * 增加item
     */
    public void addData(Iteminfo iteminfo){
        notifyItemInserted(1);
    }

    /**
     * 删除item
     */
    public void deleteData(){
        notifyItemRemoved(1);
    }


}
