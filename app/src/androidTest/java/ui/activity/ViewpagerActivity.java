package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import videodemo.R;
import view.viewpager.RotateDownPageTransformer;

/**
 * Created by admin on 2017-05-10.
 */

public class ViewpagerActivity extends Activity {
    private ViewPager mViewPager;
    private int[] mImgIds = new int[]{ R.drawable.guide_image1,
            R.drawable.guide_image2, R.drawable.guide_image3};
    private List<ImageView> mImageViews = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpager);

        initData();

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);


		mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
//		mViewPager.setPageTransformer(true, new DepthPageTransformer());
//		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mViewPager.setAdapter(new PagerAdapter()
        {
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {

                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object)
            {

                container.removeView(mImageViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @Override
            public int getCount()
            {
                return mImgIds.length;
            }
        });

    }

    private void initData()
    {
        for (int imgId : mImgIds)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            mImageViews.add(imageView);
        }
    }

}
