package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import videodemo.R;
import view.PullToRefreshListView;

/**
 * Created by admin on 2017-05-16.
 */

public class PulltorefreshListViewActivity extends Activity {

    private int j;
    private int k;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefreshlistview);
        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        final PullToRefreshListView pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pulltorefreshListView);
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("德玛西亚"+i+"区");
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        pullToRefreshListView.setAdapter(arrayAdapter);

        pullToRefreshListView.setOnPullToRefreshListener(new PullToRefreshListView.OnPullToRefreshListener() {

            @Override
            public void refrsh() {
                //1.加载数据需要停留一段时间，2.将数据添加到listview的第一个位置
                //延迟多少事件执行操作
                //参数1：Runnable：执行的操作
                //参数2：延迟的时间
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //list.add("艾欧尼亚"+(++j)+"区");
                        list.add(0, "艾欧尼亚"+(++j)+"区");
                        arrayAdapter.notifyDataSetChanged();//刷新界面的操作
                        //取消刷新操作
                        pullToRefreshListView.finish();
                    }
                }, 3000);
            }

            @Override
            public void loadmore() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        list.add("黑色玫瑰"+(++k)+"区");
                        arrayAdapter.notifyDataSetChanged();//刷新界面的操作
                        //取消刷新操作
                        pullToRefreshListView.finish();
                    }
                }, 3000);
            }
        });
    }
}

