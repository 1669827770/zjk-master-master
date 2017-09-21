package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import videodemo.R;

/**
 * Created by admin on 2017-07-17.
 */

public class ListviewMultipleEntriesActivity extends Activity {

    private ListView mListView;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listviewmultipleentries);
        mListView = (ListView) findViewById(R.id.lv);
        mList = new ArrayList<String>();
        for(int i = 0; i< 100; i++){
            mList.add(i+"");

        }
        mListView.setAdapter(new MyAdapter());
    }
    public class MyAdapter extends BaseAdapter {
        /**
         * 三种类型item
         */
        final int TYPE_1 = 0;
        final int TYPE_2 = 1;
        final int TYPE_3 = 2;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            viewHolder holder;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder = new viewHolder();
                switch (type) {
                    case TYPE_1:
                        convertView = View.inflate(getApplicationContext(), R.layout.listviewmultipleitem1, null);
                        holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
                        holder.textView = (TextView) convertView.findViewById(R.id.tv_1);
                        break;
                    case TYPE_2:
                        convertView = View.inflate(getApplicationContext(), R.layout.listviewmultipleitem2, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.tv_2);
                        break;
                    case TYPE_3:
                        convertView = View.inflate(getApplicationContext(), R.layout.listviewmultipleitem3, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.tv_3);
                        holder.imageView = (ImageView) convertView.findViewById(R.id.iv_3);
                        break;
                    default:
                        break;
                }
                convertView.setTag(holder);
            }else {
                holder = (viewHolder) convertView.getTag();
            }
            holder.textView.setText(Integer.toString(position));

            switch (type) {
                case TYPE_1:
                    holder.checkBox.setChecked(true);
                    break;
                case TYPE_2:
                    break;
                case TYPE_3:
                    holder.imageView.setBackgroundResource(R.drawable.govaffairs);
                    break;
            }
            return convertView;
        }
        /**
         * 返回条目的总数量
         */
        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return 3;
        }
        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            int p = position % 6;
            if (p == 0)
                return TYPE_1;
            else if (p < 3)
                return TYPE_2;
            else if (p < 6)
                return TYPE_3;
            else
                return TYPE_1;
        }

    }
    static class viewHolder{
        CheckBox checkBox;
        TextView textView;
        ImageView imageView;

    }

}