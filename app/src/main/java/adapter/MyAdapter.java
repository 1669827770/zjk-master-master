package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bean.GoodMan;
import videodemo.R;


/**
 * Created by Administrator on 2016/7/25.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<GoodMan> goodManList;
    public MyAdapter(Context context, List<GoodMan> goodManList) {
        this.context=context;
        this.goodManList=goodManList;
    }

    @Override
    public int getCount() {
        return goodManList==null?0:goodManList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= View.inflate(context, R.layout.goodman_item,null);
        }
        ViewHolder holder= ViewHolder.getViewHolder(convertView);
        //设置数据到控件
        GoodMan goodMan = goodManList.get(position);
        String firstLetter=goodMan.getPinyin().charAt(0)+"";
        if (position==0){
            holder.tv_pinyin.setVisibility(View.VISIBLE);
            holder.tv_pinyin.setText(firstLetter);
        }else{
            //获取上一个条目的拼音首字母
            GoodMan preGoodMan = goodManList.get(position - 1);
            String preFirstLetter=preGoodMan.getPinyin().charAt(0)+"";
            if (TextUtils.equals(preFirstLetter,firstLetter)){
                //和上一个条目的首字母相同
                holder.tv_pinyin.setVisibility(View.GONE);
            }else{
                holder.tv_pinyin.setVisibility(View.VISIBLE);
                holder.tv_pinyin.setText(firstLetter);
            }
        }
        holder.tv_name.setText(goodMan.getName());
        return convertView;
    }
    private static class ViewHolder{
        private TextView tv_pinyin;
        private TextView tv_name;

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder= (ViewHolder) convertView.getTag();
            if (holder==null){
                holder=new ViewHolder();
                holder.tv_pinyin= (TextView) convertView.findViewById(R.id.tv_pinyin);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
