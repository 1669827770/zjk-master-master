package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import videodemo.R;

/**
 * Created by admin on 2017-05-16.
 */

public class LoopViewpagerActivity extends Activity {

    private static final int VIEWPAGER_CHANGED = 0;

    private ViewPager mViewPager;

    private int[] imageResIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };

    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };
    //存放ImageView的数组
    ImageView[] imageViews = new ImageView[imageResIds.length];
    //存放点的数组
    View[] dots = new View[imageResIds.length];

    private TextView mText;
    //存放白色的点
    private View currentDot;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case VIEWPAGER_CHANGED:
                    //界面切换
                    switchPage();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loopviewpager);
        initView();
    }
    /**
     * 自动切换界面
     */
    protected void switchPage() {
        //0->1  1->2  2->3  3->4  4->0
        //获取当前显示界面的索引
        int currentItem = mViewPager.getCurrentItem();//获取当前显示界面的索引
        //如果是最后一个界面，从0开始
        //getAdapter : 获取viewpager的adapter
        if (currentItem == mViewPager.getAdapter().getCount()-1) {
            currentItem=0;
        }else{
            currentItem++;
        }
        //重新设置当前显示界面
        mViewPager.setCurrentItem(currentItem);
        //重新发送消息，进行下一次切换
        handler.sendEmptyMessageDelayed(VIEWPAGER_CHANGED, 3000);
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mText = (TextView) findViewById(R.id.viewpager_text);
        mLLDot = (LinearLayout) findViewById(R.id.ll_dot);

        //图片放到ImageView,imageView放到ViewPager中展示
        //根据图片创建imageview,有几张图片创建几个imageView
        for (int i = 0; i < imageResIds.length; i++) {
            //创建ImageView
            createImageView(i);
            //根据图片的张数，创建相应的个数的点
            createDot(i);
        }
        //当界面切换到相应的界面的时候显示相应的文本，就需要设置viewpager的界面切换监听
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        //设置第一个界面显示文本和点
        changed(0);

        //设置最大条目数
        pagesize = imageResIds.length * 1000 * 100;

        //跟listview类似
        mViewPager.setAdapter(new Myadapter());
        //设置刚进入的时候也可以从第一个界面滑动到最后一个界面
        int currentItem = pagesize/2;
        //设置显示currentItem对应的条目
        mViewPager.setCurrentItem(currentItem);//设置当前显示的条目，item:显示条目的索引
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(VIEWPAGER_CHANGED, 3000);//每隔一段时间给handler发送一个消息
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消切换操作
        handler.removeMessages(VIEWPAGER_CHANGED);//停止发送消息
    }

    /**
     * 创建点的操作
     * @param i
     */
    private void createDot(int i) {
        //保存创建的点
        dots[i] = new View(this);
        //LayoutParams : 设置view的属性
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
        //设置背景图片
        dots[i].setBackgroundResource(R.drawable.selector_dot);
        params.rightMargin = 10;//设置距离右边的距离
        //设置属性给view
        dots[i].setLayoutParams(params);
        //将view添加到点的容器中显示
        mLLDot.addView(dots[i]);
    }
    //viewpager的界面切换监听
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        //当viewpager界面切换完成的时候调用
        //position : 当前界面的索引
        @Override
        public void onPageSelected(int position) {
            //切换文本和点
            changed(position);
        }
        //当viewpager切换的时候调用
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }
        //当viewpager界面切换状态改变调用的
        //state : ViewPager状态
        @Override
        public void onPageScrollStateChanged(int state) {
            //ViewPager.SCROLL_STATE_IDLE;//空闲状态
            //ViewPager.SCROLL_STATE_DRAGGING;//拖动的状态
            //ViewPager.SCROLL_STATE_SETTLING;//拖动到最后一个的状态
            //如果是滑动状态取消自动滑动
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                handler.sendEmptyMessageDelayed(VIEWPAGER_CHANGED, 3000);
            }else{
                handler.removeMessages(VIEWPAGER_CHANGED);
            }
        }
    };

    private LinearLayout mLLDot;

    private int pagesize;
    /**
     * 切换文本和点
     * @param position
     */
    protected void changed(int position) {
        position = position % imageResIds.length;
        //切换文本
        mText.setText(descs[position]);
        //切换点
        //白色 -> 黑色   黑色 -> 白色  ，同一时刻只能有一个白点
        //判断如果上一个界面的点是白色，改为黑色
        if (currentDot != null) {
            currentDot.setSelected(false);
        }
        //设置下一个点是白色的点
        dots[position].setSelected(true);
        //保存白色的点
        currentDot = dots[position];
    }

    /**
     * 创建
     * @param i
     */
    private void createImageView(int i) {
        //因为要创建很多的imageview来使用
        imageViews[i] = new ImageView(this);
        //设置imageView显示图片
        imageViews[i].setBackgroundResource(imageResIds[i]);
    }


    private class Myadapter extends PagerAdapter {
        //设置条目的个数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pagesize;
        }
        //判断viewpager的页面的对象是否和instantiateItem返回添加界面的对象一致
        //判断当前要显示界面对象是否和原先已经创建出来的界面对象一致，一致：添加到viewpager显示，不一致：不做操作
        //view:viewpager界面的对象
        //object : instantiateItem返回的对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //添加viewPager的条目
        //container : viewpager
        //position : 条目的位置
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //01234 5%5 = 0   6%5 = 1  7%5 = 2
            position = position % imageResIds.length;
            //1.根据条目的位置获取相应的ImageView
            ImageView imageView = imageViews[position];
            //2.添加到viewpager中展示了
            container.addView(imageView);
            //3.viewpager添加了什么对象，返回什么对象，表示返回ViewPager界面的对象
            return imageView;
        }
        //销毁viewpager的条目
        //container : ViewPager
        //position : 条目的索引
        //object : instantiateItem返回的对象
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            //super.destroyItem(container, position, object);//抛出异常
            container.removeView((View)object);
        }
    }
























}
