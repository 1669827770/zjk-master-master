package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import videodemo.R;
import view.FullyGridLayoutManager;

/**
 * Created by admin on 2017-05-12.
 */

public class SViewOrRviewBattleActivity extends Activity {

    private RecyclerView mRcRecycleview;

    private List<String> mDatas;
    private HomeAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
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
//        mRcRecycleview.setLayoutManager(new LinearLayoutManager(this));
//        mRcRecycleview.setLayoutManager(new GridLayoutManager(this,4));
//设置adapter

//设置Item增加、移除动画
//        mRcRecycleview.setItemAnimator(new DefaultItemAnimator());
////添加分割线
//        mRcRecycleview.addItemDecoration(new DividerGridItemDecoration(this));

        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 2);
        mRcRecycleview.setNestedScrollingEnabled(false);
        mRcRecycleview.setLayoutManager(fullyGridLayoutManager);
        mAdapter = new HomeAdapter();
        mRcRecycleview.setAdapter(mAdapter);

//        mEnjoyLists = news.getEnjoylist();
//        MasonryAdapter adapter = new MasonryAdapter(this, mEnjoyLists);
//        mRcRecycleview.setAdapter(adapter);
//设置item之间的间隔
//        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
//        recycle_pubu.addItemDecoration(decoration);
    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(SViewOrRviewBattleActivity.this).inflate(R.layout.item_home, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));

        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
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
