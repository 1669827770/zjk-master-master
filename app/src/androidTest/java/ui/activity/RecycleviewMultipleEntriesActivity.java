package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import videodemo.R;

/**
 * Created by admin on 2017-07-17.
 */

public class RecycleviewMultipleEntriesActivity extends Activity{
    private RecyclerView mRcRecycleview;

    private List<String> mDatas;
    private HomeAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlerecycleview);
        initData();
        initView();
    }


    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    private void initView() {
        mRcRecycleview = (RecyclerView) findViewById(R.id.rc_recycleview);
//设置布局管理器
        mRcRecycleview.setLayoutManager(new LinearLayoutManager(this));
//        mRcRecycleview.setLayoutManager(new GridLayoutManager(this,4));
//设置adapter
        mAdapter = new HomeAdapter();
        mRcRecycleview.setAdapter(mAdapter);
//设置Item增加、移除动画
        mRcRecycleview.setItemAnimator(new DefaultItemAnimator());
//添加分割线
//        mRcRecycleview.addItemDecoration(new DividerGridItemDecoration(this));

    }



    class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = null;
            RecyclerView.ViewHolder viewHolder = null;
            //根据viewType生成viewHolder
            if(viewType==2){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type1, null);
                viewHolder = new MyViewHolder(view);

            }else if(viewType==3){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type2, null);
                viewHolder = new MyViewHolder2(view);


            }else if(viewType==5){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type3, null);
                viewHolder = new MyViewHolder3(view);
            }else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type4, null);
                viewHolder = new MyViewHolder4(view);
            }


            return viewHolder;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            //根据条目的类型给holder中的控件填充数据
            int itemViewType = getItemViewType(position);
            if(itemViewType==2){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.tv.setText("类型1");
            }else if(itemViewType==3){
                MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
                myViewHolder2.img_imageview.setImageResource(R.drawable.a);
            }else if(itemViewType==5){
                MyViewHolder3 myViewHolder3 = (MyViewHolder3) holder;
                myViewHolder3.btn_button.setBackgroundResource(R.drawable.a);
            }else {
                MyViewHolder4 myViewHolder4 = (MyViewHolder4) holder;
                myViewHolder4.tv_tvnum.setText(position+"");
            }

        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(position==2){
                return 2;
            }else if(position==5){
                return 5;
            }else if(position==3){
                return 3;
            }else {
                return position;
            }

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


        class MyViewHolder2 extends RecyclerView.ViewHolder
        {
            ImageView img_imageview;

            public MyViewHolder2(View view)
            {
                super(view);
                img_imageview = (ImageView) view.findViewById(R.id.img_imageview);
            }
        }

        class MyViewHolder3 extends RecyclerView.ViewHolder
        {
            Button btn_button;

            public MyViewHolder3(View view)
            {
                super(view);
                btn_button = (Button) view.findViewById(R.id.btn_button);
            }
        }

        class MyViewHolder4 extends RecyclerView.ViewHolder
        {
            TextView tv_tvnum;

            public MyViewHolder4(View view)
            {
                super(view);
                tv_tvnum = (TextView) view.findViewById(R.id.tv_tvnum);
            }
        }


    }
}
