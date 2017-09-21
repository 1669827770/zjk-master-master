package ui.activity.video;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

import videodemo.R;
import view.FullyGridLayoutManager;
import view.MyRecycleview;


/**
 * Created by zhangbing on 2017-05-12.
 * 知识点：recyclevbiew与ScroLLview的冲突
 *
 */

public class ChooseVideoFragment extends Fragment implements View.OnTouchListener,View.OnClickListener {
    int k =1;
    private int lastX, lastY;
    boolean isCompressIng = false;
    MyScrollview  my_scrollView;

    private Button toTopBtn;// 返回顶部的按钮
    private int scrollY = 0;// 标记上次滑动位置
    private View contentView;


    //视频压缩部分常量
    Double videoLength = 0.0;//视频时长
    //视频压缩数据地址
    private String currentOutputVideoPath = "/mnt/sdcard/out.mp4";
    File file;
    ProgressBar progressBar;

    private static final int SHOW_PROGRESS = 2;
    View messageLayout;
    public ArrayList<VideoInfo> allVideos;

    MyRecycleview mvideo_select_recyclerview;
    ImageView miv_back_topic2;
    Button mbtn_next;
    VideoView mvv;

    Boolean isFirstPlay = true;

    GridLayoutManager manager;
    String videoPath;
    VideoGridViewAdapter videoGridViewAdapter;
    private ArrayList<VideoInfo> videoListData;
    private final MessageHandler mMessageHandler = new MessageHandler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个就是获取视频URi的核心方法，知道本方法，本类就ok了
        allVideos = getAllVideoFiles(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(allVideos.size()>0){
            //覆盖videoview的图


            ArrayList<VideoInfo> videoInfos = new ArrayList<>();
            for (int i = 0; i <allVideos.size() ; i++) {
                VideoInfo videoInfo = allVideos.get(i);
                String videoName = videoInfo.getVideoName();

                if(videoInfo.getDuration()>5000&&!videoName.contains("yang")){
                    videoInfos.add(videoInfo);
                }
            }
            videoPath = videoInfos.get(0).getVideoPath();
            this.videoListData = videoInfos;
            if(videoInfos.size()==0){
                Toast.makeText(getContext(),"您的手机内没有大于5秒的视频", Toast.LENGTH_SHORT).show();
            }

            videoGridViewAdapter = new VideoGridViewAdapter(getContext(), videoInfos);


            manager = new GridLayoutManager(getActivity(), 4);
            messageLayout = inflater.inflate(R.layout.fragment_video, container, false);

            mvideo_select_recyclerview = (MyRecycleview) messageLayout.findViewById(R.id.video_select_recyclerview);
            my_scrollView = (MyScrollview) messageLayout.findViewById(R.id.my_scrollView);
            miv_back_topic2 = (ImageView) messageLayout.findViewById(R.id.iv_back_topic2);

            mbtn_next = (Button) messageLayout.findViewById(R.id.btn_next);
            mvv = (VideoView) messageLayout.findViewById(R.id.vv);
            progressBar = (ProgressBar) messageLayout.findViewById(R.id.progressBar);
            
            mvideo_select_recyclerview.addItemDecoration(new SpacesItemDecoration(15));
            mvideo_select_recyclerview.setHasFixedSize(true);

            FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(getActivity(), 4);
            mvideo_select_recyclerview.setNestedScrollingEnabled(false);
            mvideo_select_recyclerview.setLayoutManager(fullyGridLayoutManager);

            mvideo_select_recyclerview.setAdapter(videoGridViewAdapter);

            mvv.setOnTouchListener(this);

            miv_back_topic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();

                }
            });



            //下一步的点击方法
            mbtn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ChooseVideoFragment.this.videoPath == null) {
                        Toast.makeText(getActivity(), "请先选择视频", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                isFirstPlay = true;
                    //获取视频时长  计算压缩进度用
                    MediaMetadataRetriever retr = new MediaMetadataRetriever();
                    retr.setDataSource(videoPath);
                    String time = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取视频时长
                    int i = Integer.parseInt(time);
                    if(i<5000){
                        Toast.makeText(getActivity(), "视频不能低于5秒，请重新选择", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = getActivity().getIntent();
//                    intent.setClass(getActivity(), TrimmerActivity.class);
                    intent.putExtra("chooseVideoFragment", videoPath);
                    startActivity(intent);
                    //解决视频裁剪界面返回来，vieoview没有照片展示问题   ,关闭掉还可以操作
//                    mPlayView.setVisibility(View.VISIBLE);
//                    icon_video_thumbnail.setVisibility(View.VISIBLE);
//                    Glide.with(getContext()).load(videoPath).thumbnail(0.1f).into(icon_video_thumbnail);


                }

            });

            final GestureDetector gestureDetector = new
                    GestureDetector(getContext(),
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {

                            if(isFirstPlay){   //解决了视频返回来的时候播放那个连接的问题
                                onClickVideoPlayPause(allVideos.get(0).getVideoPath());
                            }else{
                                onClickVideoPlayPause(videoPath);
                            }
                            return true;
                        }
                    }
            );

            mvv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, @NonNull MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });


            videoGridViewAdapter.setItemClickCallback(new SingleCallback<Boolean, VideoInfo>() {
                @Override
                public void onSingleCallback(Boolean isSelected, VideoInfo video) {
                    if (video != null) {
                        ChooseVideoFragment.this.videoPath = video.getVideoPath();
                        placevideo(ChooseVideoFragment.this.videoPath);//播放视频
                        my_scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }

                }
            });

       placevideo(videoInfos.get(0).getVideoPath());//播放视频

        }else {
            Toast.makeText(getContext(), "您的手机没有视频可获取喔", Toast.LENGTH_SHORT).show();
        }
        return messageLayout;
    }



    public void placevideo(String s) {
        //设置视频路径
        mvv.setVideoURI(Uri.parse(s));
        //开始播放视频
        mvv.start();
    }

    public boolean onTouch(MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) _view
//                        .getLayoutParams();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = mvv.getLeft() + dx;
                int top = mvv.getTop() + dy;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mvv
                        .getLayoutParams();
//                layoutParams.height=IMAGE_SIZE;
//                layoutParams.width = IMAGE_SIZE;
                layoutParams.leftMargin =left;
                layoutParams.topMargin =top;
                mvv.setLayoutParams(layoutParams);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
        }
        mvv.invalidate();
        return true;
    }

    private void onClickVideoPlayPause(String s) {
        //如果一进来还没有点击播放过(或者点击下一步之后)，那么点击播放第一个，
//        icon_video_thumbnail.setVisibility(View.INVISIBLE);
        if (isFirstPlay) {
            //设置视频路径
            mvv.setVideoURI(Uri.parse(s));
            //开始播放视频
            mvv.start();

            isFirstPlay = false;  //只要播放过，再点击就走else
        } else {

            if (mvv.isPlaying()) {

                mMessageHandler.removeMessages(SHOW_PROGRESS);
                mvv.pause();
            } else {
                
                mMessageHandler.sendEmptyMessage(SHOW_PROGRESS);
                mvv.start();
            }
        }
    }

    /**
     * 需要设计成异步的
     *
     * @param mContext
     * @return
     */
    public ArrayList<VideoInfo> getAllVideoFiles(Context mContext) {
        VideoInfo video;
        ArrayList<VideoInfo> videos1 = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
//            Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
//                    null, null, MediaStore.Video.Media.DATE_MODIFIED + " desc");
            Cursor cursor =getContext(). getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,null,
                    MediaStore.Images.Media.DATE_MODIFIED);

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
    

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
    
    private class MessageHandler extends Handler {

//        @NonNull
//        private final WeakReference<HgLVideoTrimmer> mView;

        MessageHandler() {
//            mView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
//            HgLVideoTrimmer view = mView.get();
            if (mvv == null) {
                return;
            }

//            view.notifyProgressUpdate(true);
            if (mvv.isPlaying()) {
                sendEmptyMessageDelayed(0, 10);
            }
        }
    }

}

