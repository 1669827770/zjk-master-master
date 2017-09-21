package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import videodemo.R;
import view.CustomTransformer;

/**
 * Created by admin on 2017-07-26.
 * https://juejin.im/post/5933c65d0ce463005717cbe9
 */

public class MeiZuApplicationsBannerViewActivity extends Activity {

    private ViewPager mViewPager;

    private int[] imageResIds = {
            R.mipmap.image1,
            R.mipmap.image2,
            R.mipmap.image3,
            R.mipmap.image4,
            R.mipmap.image5,
            R.mipmap.image6,
            R.mipmap.image7,
            R.mipmap.image8,
    };

    //存放ImageView的数组
    ImageView[] imageViews = new ImageView[imageResIds.length];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meizuapplicationsbannerview);

        initView();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new Myadapter());
        mViewPager.setPageTransformer(true, new CustomTransformer());

        for (int i = 0; i < imageResIds.length; i++) {
            //因为要创建很多的imageview来使用
            imageViews[i] = new ImageView(this);
            //设置imageView显示图片
            imageViews[i].setBackgroundResource(imageResIds[i]);
        }

    }

    private class Myadapter extends PagerAdapter {
        //设置条目的个数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViews.length;
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
//            position = position % imageResIds.length;
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
            container.removeView((View) object);
        }
    }
}
