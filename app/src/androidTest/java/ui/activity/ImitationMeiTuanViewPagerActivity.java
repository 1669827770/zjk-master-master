package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.GridViewAdapter;
import adapter.ViewPagerAdapter;
import bean.MTbean;
import videodemo.R;

/**
 * Created by admin on 2017-07-31.
 * https://github.com/HeyMouser/MeiTuan
 */

public class ImitationMeiTuanViewPagerActivity extends Activity{


    ViewPager mPager;
    LinearLayout mLlDot;

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    private List<View> mPagerList;   //这个是viewpager有多少页
    private List<MTbean> mDatas;      //这个是一共有多少数据
    private LayoutInflater inflater;
    private int pageCount;//总页数
    private int pageSize = 10;//每一页的个数
    private int curIndex = 0;//当前显示的事第几页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imitationmeituanviewpager);

        mPager = (ViewPager) findViewById(R.id.viewpager);
        //指示点
        mLlDot = (LinearLayout) findViewById(R.id.ll_dot);
        /**
         * 初始化数据
         */
        initDatas();
        //创建布局加载器
        inflater = LayoutInflater.from(this);

        //总页数=总数/每页的个数，取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);

        mPagerList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出的一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, null);
            /**
             * 参数二是总数据，参数三是第几页，参数四有多少页viewpager
             */
            gridView.setAdapter(new GridViewAdapter(this, mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(ImitationMeiTuanViewPagerActivity.this, mDatas.get(pos).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置viewpageAdapter
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置小圆点
        setOvalLayout();

    }

    private void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        //默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //取消选中
                mLlDot.getChildAt(curIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                //选中
                mLlDot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);

                curIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<MTbean>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            mDatas.add(new MTbean(titles[i], imageId));
        }
        Log.i("数据源", "---->" + mDatas.size());
        Log.i("数据源", "---->" + mDatas.toString());
    }
}
