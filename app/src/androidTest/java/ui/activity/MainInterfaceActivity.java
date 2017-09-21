package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import base.BasePager;
import base.impl.GovaffairsPager;
import base.impl.HomePager;
import base.impl.NewscenterPager;
import base.impl.SettingsPager;
import base.impl.SmartservicePager;
import videodemo.R;
import view.NoscrollViewPager;

/**
 * Created by admin on 2017-06-11.
 */

public class MainInterfaceActivity extends Activity {

    private List<BasePager> pagers;
    private NoscrollViewPager mVpContentPagers;
    /**
     * 首页
     */
    private RadioButton mRbBottomHome;
    /**
     * 新闻中心
     */
    private RadioButton mRbBottomNewscenter;
    /**
     * 智慧服务
     */
    private RadioButton mRbBottomSmartservice;
    /**
     * 政务
     */
    private RadioButton mRbBottomGovaffairs;
    /**
     * 设置
     */
    private RadioButton mRbBottomSettings;
    private RadioGroup mRgContentBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininterface);
        initView();
        initData();


    }

    private void initView() {
        mVpContentPagers = (NoscrollViewPager) findViewById(R.id.vp_content_pagers);
        mRbBottomHome = (RadioButton) findViewById(R.id.rb_bottom_home);
        mRbBottomNewscenter = (RadioButton) findViewById(R.id.rb_bottom_newscenter);
        mRbBottomSmartservice = (RadioButton) findViewById(R.id.rb_bottom_smartservice);
        mRbBottomGovaffairs = (RadioButton) findViewById(R.id.rb_bottom_govaffairs);
        mRbBottomSettings = (RadioButton) findViewById(R.id.rb_bottom_settings);
        mRgContentBottom = (RadioGroup) findViewById(R.id.rg_content_bottom);
    }

    protected void initData() {
        // 更新正文界面中的5个页面
        // 准备5个界面作为ViewPager的数据

        pagers = new ArrayList<BasePager>();
        pagers.add(new HomePager(MainInterfaceActivity.this));
        pagers.add(new NewscenterPager(MainInterfaceActivity.this));
        pagers.add(new SmartservicePager(MainInterfaceActivity.this));
        pagers.add(new GovaffairsPager(MainInterfaceActivity.this));
        pagers.add(new SettingsPager(MainInterfaceActivity.this));

        mVpContentPagers.setAdapter(new MyAdapter());

        // 监听ViewPager的滑动，当滑动到某一页时，才加载当前页的数据
        mVpContentPagers.setOnPageChangeListener(new MyOnPageChangeListener());

        // 初始化首页数据
        pagers.get(0).initData();

        // 监听底部单选按钮，实现单选按钮与5个界面的关联
        mRgContentBottom.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        // 默认选中首页
        mRgContentBottom.check(R.id.rb_bottom_home);
    }
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 当单选按钮发生变化时调用
            switch (checkedId) {
                case R.id.rb_bottom_home:
                    mVpContentPagers.setCurrentItem(0,false);//参数2 是否带滑动效果
                    enableSlidingMenu(false);
                    break;
                case R.id.rb_bottom_newscenter:
                    mVpContentPagers.setCurrentItem(1,false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_smartservice:
                    mVpContentPagers.setCurrentItem(2,false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_govaffairs:
                    mVpContentPagers.setCurrentItem(3,false);
                    enableSlidingMenu(true);
                    break;
                case R.id.rb_bottom_settings:
                    mVpContentPagers.setCurrentItem(4,false);
                    enableSlidingMenu(false);
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 禁用侧滑菜单的滑动效果
     * @author h
     *
     */
    private void enableSlidingMenu(boolean enable){
//        MainUI mainUI = (MainUI) mActivity;
//        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
//        if(enable){
//            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        }else{
//            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 当滑动到某一页时，才加载当前页的数据
            pagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
			/*// 根据位置返回5个不同的布局
			View view = null;
			if (position == 0) {
				view = View.inflate(mActivity, R.layout.home_test, null);
			}
			if (position == 1) {
				view = View.inflate(mActivity, R.layout.newscenter_test, null);
			}
			if(position == 2){

			}
			// 。。。

			// 根据位置更新布局
			if (position == 0) {
				view = view.findViewById();
				// 访问网络
			}
			if (position == 1) {
				view = view.findViewById();
				// 访问网络
			}
			container.addView(view);
			return view;*/

            /**
             * Pager{
             * 	initView();
             * 	initData();
             * }
             *
             * new HomePager();
             *
             * if (position == 0) {
             * HomePager.initview()
             * }
             *
             * if (position == 0) {
             * HomePager.initData()
             * }
             *
             * ArrayList list = new ArrayList<Pager>();
             * list.add(new HomePager());
             *
             * Pager pager = list.get(position);
             * pager.initView();
             * pager.initData();
             */

            // 根据位置获取5个界面中的相应的对象
            BasePager pager = pagers.get(position);
            // 把对象身上的布局添加给ViewPager
            View view = pager.rootView;
            container.addView(view);
            // 更新对象身上的布局
//			pager.initData();// 在instantiateItem方法里面调用initData方法，会因为instantiateItem的预加载导致我们会预加载数据，造成流量浪费
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    /**
     * 对外提供获取新闻中心对象的方法
     *
     */

    public NewscenterPager getNewscenterPager(){
        return (NewscenterPager) pagers.get(1);
    }
}
