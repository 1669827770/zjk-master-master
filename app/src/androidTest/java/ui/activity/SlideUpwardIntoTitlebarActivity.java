package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import videodemo.R;

/**
 * Created by admin on 2017-07-14.
 */

public class SlideUpwardIntoTitlebarActivity extends Activity {




    private String[] names = {
            "日历选择-日期没有限制&全向拖动", "日历选择-日期有限制&单向", "选项卡切换-三角形", "选项卡切换-线形", "时间选择",
            "时间选择（占位用的）", "加载等待动画", "饼图", "进度图", "雷达图",
            "圆形图片", "滑动按钮", "温度计", "进度条按钮", "页面下方小点",
            "tab小点", "日期滚轮", "时间滚轮", "全套滚轮", "tab条形",
            "倒计时View", "商品列表", "支付宝咻一咻", "系统自带的抽屉用法演示", "现在较流行的抽屉样式",
            "带涟漪的Layout", "渐变的View", "通讯录", "添加快捷方式", "删除快捷方式",
            "水滴加载动画", "跑马灯", "折线图", "刮刮卡", "转动的心（未完成）",
            "文字跳跳加载动画1","文字跳跳加载动画2","会动的返回键(模仿谷歌抽屉箭头)"};
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideupwardintotitlebar);

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HomeAdapter());

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(SlideUpwardIntoTitlebarActivity.this).inflate(R.layout.item_home, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(names[position]);
        }

        @Override
        public int getItemCount()
        {
            return names.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }

}
