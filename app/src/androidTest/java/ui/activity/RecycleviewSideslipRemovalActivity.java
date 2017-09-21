package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomAdapter;
import videodemo.R;
import view.CustomRecyclerView;

/**
 * Created by admin on 2017-07-25.
 */

public class RecycleviewSideslipRemovalActivity extends Activity {

    private CustomRecyclerView recyclerView;
    private List<String> mDatas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycleviewsideslipremoval);

        initView();
    }


    private void initView() {
        recyclerView = (CustomRecyclerView) findViewById(R.id.recycler_vew);
        //设置布局
        //LinearLayoutManager 线性布局
        //GriderlayoutManager 网格布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //  recyclerView.addItemDecoration(new DividerDecoration(this,1));
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        initData();
        //设置适配器
        final CustomAdapter customAdapter = new CustomAdapter(mDatas,this);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setOnItemClickListenre(new CustomRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDatas.remove(position);
                customAdapter.notifyItemRemoved(position);
            }
        });
        // SimpleItemCallBack simpleItemCallBack = new SimpleItemCallBack(customAdapter,mDatas);
        //ItemTouchHelper helper = new ItemTouchHelper(simpleItemCallBack);
        // helper.attachToRecyclerView(recyclerView);
    }
    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'X'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

}
