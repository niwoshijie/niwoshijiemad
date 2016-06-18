package mainpagers.mpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import liushaobo.mad.R;

/**
 * Created by LiuShao on 2016/6/6.
 */
public class MaoAdapter extends RecyclerView.Adapter<MaoAdapter.MaoRecycleHolder>{

    private List<String> dataString;//文本信息
    private int[] images;
    private Context context;
    public void setResourse(List<String> textString,int[] image,Context context){
        this.dataString = dataString;
        this.images = image;
        this.context = context;
    }

    @Override
    public MaoRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.mao_pager_item,null);
        return new MaoRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MaoRecycleHolder holder, int position) {

      }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MaoRecycleHolder extends RecyclerView.ViewHolder{


        @ViewInject(R.id.mao_pager_text)
        private TextView mao_pager_text;


        public MaoRecycleHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }

        public void setData(){

        }


    }

}
