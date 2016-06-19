package mainpagers.mpackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/6/6.
 */
public class MaoAdapter extends RecyclerView.Adapter<MaoAdapter.MaoRecycleHolder>{

    private String[]  resourseText;
    private int[]  resourseImage;

    public MaoAdapter(String[] resourseText, int[] resourseImage) {
        this.resourseText = resourseText;
        this.resourseImage = resourseImage;
    }

    @Override
    public MaoRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.mao_pager_item,null);
        return new MaoRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MaoRecycleHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return resourseText.length;
    }

    public class MaoRecycleHolder extends RecyclerView.ViewHolder{


        @ViewInject(R.id.mao_pager_item_tv)
        private TextView mao_pager_text;

        @ViewInject(R.id.mao_pager_item_iamge)
        private ImageView mao_pager_item_iamge;

        public MaoRecycleHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }

        public void setData(int itemPosition){
            mao_pager_text.setText(resourseText[itemPosition]);
            mao_pager_item_iamge.setImageResource(resourseImage[itemPosition]);

        }


    }

}
