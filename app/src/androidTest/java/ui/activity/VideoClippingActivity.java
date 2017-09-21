package ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

import ui.activity.video.SingleCallback;
import ui.activity.video.SpacesItemDecoration;
import ui.activity.video.VideoGridViewAdapter;
import ui.activity.video.VideoInfo;
import videodemo.R;
import view.SolvingWithoutInertiaScrollview;

import static util.Utils.getContext;

/**
 * Created by admin on 2017-07-18.
 */

public class VideoClippingActivity extends Activity {

    public ArrayList<VideoInfo> allVideos;
    private ImageView mIvBack;
    /**
     * 视频
     */
    private TextView mTvTitle;
    /**
     * 下一步
     */
    private Button mBtnNext;
    private RelativeLayout mLyTopBar;
    private VideoView mVvVideo;
    private RecyclerView mVideoRecyclerview;
    private SolvingWithoutInertiaScrollview mSlScrollview;
    /**
     * recycleview的条目点击后对应播放视频的路径
     */
    private String videoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoclipping);

        //这个就是获取视频URi的核心方法，知道本方法，本类就ok了
        allVideos = getAllVideoFiles(getContext());
        initView();
        initData();
        initLisener();


    }

    private void initLisener() {

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (videoPath == null) {
                    Toast.makeText(getContext(), "请先选择视频", Toast.LENGTH_SHORT).show();
                    return;
                }


                /**
                 * 获取视频时长
                 */
                MediaMetadataRetriever retr = new MediaMetadataRetriever();
                retr.setDataSource(videoPath);
                String time = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取视频时长
                int i = Integer.parseInt(time);
                if(i<5000){
                    Toast.makeText(getContext(), "视频不能低于5秒，请重新选择", Toast.LENGTH_SHORT).show();
                    return;
                }


                /**
                 * 点击跳转
                 */
                Intent intent = new Intent(getApplicationContext(),VideoTrimActivity.class);
                intent.putExtra("VideoPath", videoPath);
                startActivity(intent);

                //解决视频裁剪界面返回来，vieoview没有照片展示问题   ,关闭掉还可以操作
//                Glide.with(getContext()).load(videoPath).thumbnail(0.1f).into(icon_video_thumbnail);

            }
        });

    }

    private void initData() {
        /**
         * Recyclerview填充数据
         */
        VideoGridViewAdapter videoGridViewAdapter = new VideoGridViewAdapter(getApplication(), allVideos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        mVideoRecyclerview.addItemDecoration(new SpacesItemDecoration(15));
        mVideoRecyclerview.setHasFixedSize(true);
        mVideoRecyclerview.setAdapter(videoGridViewAdapter);
        mVideoRecyclerview.setLayoutManager(gridLayoutManager);


        /**
         * Recyclerview的条目点击事件
         */
        videoGridViewAdapter.setItemClickCallback(new SingleCallback<Boolean, VideoInfo>() {
            @Override
            public void onSingleCallback(Boolean isSelected, VideoInfo video) {
                if (video != null) {
                    videoPath = video.getVideoPath();
                    placevideo(videoPath);//播放视频
                    mSlScrollview.fullScroll(ScrollView.FOCUS_UP);
                }

            }
        });
    }


    /**
     * 需要设计成异步的
     *获取所有视频
     * @param mContext
     * @return
     */
    public ArrayList<VideoInfo> getAllVideoFiles(Context mContext) {
        VideoInfo video;
        ArrayList<VideoInfo> videos1 = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, MediaStore.Video.Media.DATE_MODIFIED + " desc");
            while (cursor.moveToNext()) {
                video = new VideoInfo();

                if (cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)) != 0) {
                    video.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
                    video.setVideoPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                    video.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)));
                    video.setVideoName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                    videos1.add(video);
                }
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos1;
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mLyTopBar = (RelativeLayout) findViewById(R.id.ly_top_bar);
        mVvVideo = (VideoView) findViewById(R.id.vv_video);
        mVideoRecyclerview = (RecyclerView) findViewById(R.id.video_recyclerview);
        mSlScrollview = (SolvingWithoutInertiaScrollview) findViewById(R.id.sl_scrollview);
    }

    public void placevideo(String s) {
        //设置视频路径
        mVvVideo.setVideoURI(Uri.parse(s));
        //开始播放视频
        mVvVideo.start();

    }
}
