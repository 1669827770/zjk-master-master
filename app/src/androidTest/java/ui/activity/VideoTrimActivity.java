package ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ui.fragment.VideoCoverFragment;
import ui.fragment.VideoTrimFragment;
import videodemo.R;

/**
 * Created by admin on 2017-07-18.
 */

public class VideoTrimActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView mIvBack;
    private Button mBtnNext;
    private RelativeLayout mLyTop;
    private FrameLayout mTrimContent;
    /**
     * 下面两个封面与裁剪按钮
     */
    private Button mVideoTrim;
    private Button mVideoCover;
    /**
     * 两个fragment
     */
    VideoCoverFragment videoCoverFragment;
    VideoTrimFragment videoTrimFragment;
    /**
     * 从视频页面点击传过来的视频路径
     */
   private String videopath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videotrim);
        initView();
        videopath = getIntent().getStringExtra("VideoPath");
        setTabSelection(0);

    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {

        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
/**
 * 但是getSupportFragmentManager()  有其运用范围，只能在部分activity中运用，不能再activity中运用。
 * 当遇到getSupportFragmentManager()  没定义的问题时，修改下activity为FragmentActivity或者AppCompatActivity。
 */
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();   // 开启一个Fragment事务
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(fragmentTransaction);

        switch (index) {
            case 0:
                mVideoTrim.setTextColor(Color.rgb(24, 195, 194));
                if (videoTrimFragment == null) {
                  // 如果MessageFragment为空，则创建一个并添加到界面上
                    videoTrimFragment = new VideoTrimFragment();
                    /**
                     * Activity跳转framgnet传递path
                     */
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("VideoPath", videopath);
                    videoTrimFragment.setArguments(bundle1);
                    fragmentTransaction.add(R.id.trim_content, videoTrimFragment, "videoTrimFragment");
                } else {
//                    // 如果MessageFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(videoTrimFragment);
//
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色;
                mVideoCover.setTextColor(Color.rgb(24, 195, 194));
                if (videoCoverFragment == null) {
//                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    videoCoverFragment = new VideoCoverFragment();
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("path2", path);   //bundle不能共用，这是一个坑
//                    coverFragment.setArguments(bundle2);
                    fragmentTransaction.add(R.id.trim_content, videoCoverFragment, "videoCoverFragment");
                } else {
//                    // 如果ContactsFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(videoCoverFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (videoCoverFragment != null) {
            transaction.hide(videoCoverFragment);
        }
        if (videoTrimFragment != null) {
            transaction.hide(videoTrimFragment);
        }

    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mVideoCover.setTextColor(Color.parseColor("#2b2b2b"));
        mVideoTrim.setTextColor(Color.parseColor("#2b2b2b"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                break;
            case R.id.video_trim:
                setTabSelection(0);
                break;
            case R.id.video_cover:
                setTabSelection(1);
                break;
        }
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);
        mLyTop = (RelativeLayout) findViewById(R.id.ly_top);
        mTrimContent = (FrameLayout) findViewById(R.id.trim_content);
        mVideoTrim = (Button) findViewById(R.id.video_trim);
        mVideoTrim.setOnClickListener(this);
        mVideoCover = (Button) findViewById(R.id.video_cover);
        mVideoCover.setOnClickListener(this);
    }

}
