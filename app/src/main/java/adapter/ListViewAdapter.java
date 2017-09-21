package adapter;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import videodemo.R;

public class ListViewAdapter extends BaseAdapter {

	List<String> list = new ArrayList<String>();
	//构造代码块
	{
		for (int i = 0; i < 30; i++) {
			list.add(10000+i+"");
		}
		
	}
	private Context mContext;
	public ListViewAdapter(Context context){
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View view = View.inflate(mContext, R.layout.listview_item, null);
		ImageView icon = (ImageView) view.findViewById(R.id.icon);
		TextView text = (TextView) view.findViewById(R.id.text);
		ImageView delete = (ImageView) view.findViewById(R.id.delete);
		
		text.setText(list.get(position));
		
		//删除数据
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//删除数据，更新界面
				list.remove(position);
				//更新界面
				notifyDataSetChanged();
			}
		});
		
		return view;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
 