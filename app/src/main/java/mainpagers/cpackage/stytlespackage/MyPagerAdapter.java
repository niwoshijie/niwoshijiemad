package mainpagers.cpackage.stytlespackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class MyPagerAdapter<T> extends BaseAdapter {
	protected List<T> dataList;
	protected Context context;
	protected LayoutInflater inflate;
	public MyPagerAdapter(List<T> dataList, Context context){
		this.dataList = dataList;
		this.context = context;
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		if (dataList != null) {
			return dataList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
