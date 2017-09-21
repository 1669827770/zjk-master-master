package ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import videodemo.R;
import view.MyScrollView;

/**
 * Created by admin on 2017-05-19.
 */

public class TransparencyToolbarActivity extends AppCompatActivity implements MyScrollView.onScrollChangedListener {

    private TextView mTvTitle;
    private ImageView mIvTop;
    private int mHeight;
    private MyScrollView mScrollView;
    private RelativeLayout mParentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_transparency_toolbar);


        mScrollView = (MyScrollView) findViewById(R.id.scroll_view);
        mTvTitle = (TextView) findViewById(R.id.tv_title);//标题栏
        mIvTop = (ImageView) findViewById(R.id.iv_top); //图片

        mParentView = (RelativeLayout) findViewById(R.id.parent);
        mScrollView.addOnScrollChangedListener(this);

        mParentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mParentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mHeight = mIvTop.getHeight();
                onScrollChanged(mScrollView.getScrollY());
            }
        });

    }
    //这个重写就相当于是点击的时候的onclickd
    @Override
    public void onScrollChanged(int y) {

        if (y <= 0) {//未滑动
            mTvTitle.setBackgroundColor(Color.argb((int) 0, 31, 100, 240));
        } else if (y > 0 && y <= mHeight) { //滑动过程中 并且在mHeight之内
            float scale = (float) y / mHeight;
            float alpha = (255 * scale);
            mTvTitle.setTextColor(Color.argb((int) alpha, 255, 255, 255));
            mTvTitle.setBackgroundColor(Color.argb((int) alpha, 31, 100, 240));
        } else {//超过mHeight
            mTvTitle.setBackgroundColor(Color.argb((int) 255, 31, 100, 240));
        }
    }

}