package ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import bean.VideoItem;
import util.StringsUtils;
import videodemo.R;
import view.MobilePlayerVideoView;


/**
 * Created by Administrator on 2016/8/12.
 */
public class VideoPlayerActivity extends Activity implements View.OnClickListener{

    private static final int MSG_UPDATE_SYSTEM_TIME = 0;
    private static final int MSG_UPDATE_POSTITION = 1;
    private static final int MSG_HIDE_CONTROLOR = 2;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_SYSTEM_TIME:
                    startUpdateSystemTime();
                    break;
                case MSG_UPDATE_POSTITION:
                    startUpdatePosition();
                    break;
                case MSG_HIDE_CONTROLOR:
                    hideControlor();
                    break;
            }
        }
    };

    private MobilePlayerVideoView videoView;
    private ImageView iv_pause;
    private TextView tv_title;
    private VideoReceiver mVideoReceiver;
    private ImageView iv_battery;
    private TextView tv_system_time;
    private ImageView iv_mute;
    private SeekBar sk_volume;
    private AudioManager mAudioManager;
    private int mCurrentVolume;
    private float mStartY;
    private int mStartVolume;
    private View alpha_cover;
    private float mStartAlpha;
    private TextView tv_position;
    private SeekBar sk_position;
    private TextView tv_duration;
    private ImageView iv_pre;
    private ImageView iv_next;
    private ArrayList<VideoItem> videoItems;
    private int position;
    private LinearLayout ll_top;
    private LinearLayout ll_bottom;
    private GestureDetector gestureDetector;
    /** 如果为 true，说明面板是显示状态 */
    private boolean isControlorShowing;
    private ImageView iv_fullscreen;
    private View loading_cover;
    private ProgressBar pb_buffering;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        initView();
        initData();
        initListener();
    }




    public void initView() {
        videoView = (MobilePlayerVideoView) findViewById(R.id.videoview);
        alpha_cover = findViewById(R.id.video_alpha_cover);
        ll_top = (LinearLayout) findViewById(R.id.video_ll_top);
        ll_bottom = (LinearLayout) findViewById(R.id.video_ll_bottom);
        loading_cover = findViewById(R.id.video_loading_cover);
        pb_buffering = (ProgressBar) findViewById(R.id.video_pb_buffering);

        // 顶部按钮
        tv_title = (TextView) findViewById(R.id.video_tv_title);
        iv_battery = (ImageView) findViewById(R.id.video_iv_battery);
        tv_system_time = (TextView) findViewById(R.id.video_tv_system_time);
        iv_mute = (ImageView) findViewById(R.id.video_iv_mute);
        sk_volume = (SeekBar) findViewById(R.id.video_sk_volume);

        // 底部按钮
        tv_position = (TextView) findViewById(R.id.video_tv_position);
        sk_position = (SeekBar) findViewById(R.id.video_sk_position);
        tv_duration = (TextView) findViewById(R.id.video_tv_duration);
        iv_pause = (ImageView) findViewById(R.id.video_iv_pause);
        iv_pre = (ImageView) findViewById(R.id.video_iv_pre);
        iv_next = (ImageView) findViewById(R.id.video_iv_next);
        iv_fullscreen = (ImageView) findViewById(R.id.video_iv_fullscreen);
    }


    public void initListener() {

        // 顶部按钮
        iv_mute.setOnClickListener(this);

        // 底部按钮
        iv_pause.setOnClickListener(this);
        iv_pre.setOnClickListener(this);
        iv_next.setOnClickListener(this);
        iv_fullscreen.setOnClickListener(this);

        /**
         * 音量与视频进度的监听
         */
        OnVideoSeekBarChangeListener onSeekBarChangeListener = new OnVideoSeekBarChangeListener();
        sk_volume.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sk_position.setOnSeekBarChangeListener(onSeekBarChangeListener);

        /**
         *  注册手势监听
         */
        gestureDetector = new GestureDetector(this, new OnVideoGestureListener());

        /**
         *  注册视频监听，分别是准备，完成，错误，信息，要熟悉
         */
        videoView.setOnPreparedListener(new OnVideoPreparedListener());
        videoView.setOnCompletionListener(new OnVideoCompletionListener());
        videoView.setBufferingUpdateListener(new OnVideoBufferingUpdateListener());
        videoView.setOnErrorListener(new OnVideoErrorListener());
        videoView.setOnInfoListener(new OnVideoInfoListener());

        /**
         * 电池部分  注册广播
         */
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mVideoReceiver = new VideoReceiver();
        registerReceiver(mVideoReceiver, filter);
    }


    public void initData() {
        /**
         * if里面是从手机的文件管理中的视频点击播放的
         * else是正常从视频列表界面点击进入的
         */
        Uri uri = getIntent().getData();
//        logE("VideoPlayerActivity.initData,uri="+uri);
        if (uri!=null){
            // 外部启动
            videoView.setVideoURI(uri);
            iv_pre.setEnabled(false);
            iv_next.setEnabled(false);
            // schema://host:port/path
            // file:///mnt/sdcard/Download/video/oppo%20-1.mp4
            tv_title.setText(uri.getPath());
        }else{
            // 获取数据
            videoItems = (ArrayList<VideoItem>) getIntent().getSerializableExtra("videoItems");
//        logE("VideoPlayerActivity.initData,videoItems="+videoItems);
            position = getIntent().getIntExtra("position", 0);

            playItem();
        }

        /**
         * 系统时间
         */
        startUpdateSystemTime();

        /**
         *  初始化音量进度条
         */
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 初始化 max 值的时候，会使用布局文件的设置更新一次进度
        sk_volume.setMax(maxVolume);

        int currentVolume = getCurrentVolume();
//        logE("VideoPlayerActivity.initData,max="+maxVolume+";cureent="+currentVolume);
        sk_volume.setProgress(currentVolume);

        // 初始时屏幕最亮
        ViewCompat.setAlpha(alpha_cover, 0);

        // 隐藏控制面板
        initHideControlor();

        // 隐藏缓冲提示
        pb_buffering.setVisibility(View.GONE);
    }

    /** 初始化界面时隐藏控制面板 */
    private void initHideControlor() {
        // 在 onCreate 过程中有两种获取高度的方式：getHeight、getMeasuredHeight
        // 1. 使用 getMeasuredHeight
        ll_top.measure(0,0);
        int topH = ll_top.getMeasuredHeight();
        ViewCompat.animate(ll_top).translationY(-topH).setDuration(2000).start();
//        logE("VideoPlayerActivity.initHideControlor,getHeight="+ll_top.getHeight()+";getMeasuredHeight="+topH);

        // 2. 使用 getHeight
        ll_bottom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 当布局已经计算完成后会被回调
                ll_bottom.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewCompat.animate(ll_bottom).translationY(ll_bottom.getHeight()).setDuration(2000).start();
//                logE("VideoPlayerActivity.onGlobalLayout,getHeight="+ll_bottom.getHeight());
            }
        });

        // 面板被隐藏，修改标识为 false
        isControlorShowing = false;
    }

    /** 播放 position 位置的视频 */
    private void playItem() {
        // 获取视频对象
        VideoItem videoItem = videoItems.get(position);

        // 获取数据
//        VideoItem videoItem = (VideoItem) getIntent().getSerializableExtra("videoItem");
//        logE("VideoPlayerActivity.initData,item="+videoItem);

        // 播放视频
        videoView.setVideoPath(videoItem.getPath());

        // 系统自带的播放控制器
//        videoView.setMediaController(new MediaController(this));

        // 更新标题
        tv_title.setText(videoItem.getTitle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mVideoReceiver);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 让手势分析器分析触摸事件
        gestureDetector.onTouchEvent(event);
        
//        最终音量 = 起始音量 + 变化的音量
//        变化的音量 = 划过屏幕的百分比 * 最大音量
//        划过屏幕的百分比 = 划过屏幕的距离 / 屏幕的高度
//        划过屏幕的距离 = 当前手指位置 - 起始手指位置
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 起始手指位置
                mStartY = event.getY();
                // 起始音量
                mStartVolume = getCurrentVolume();
                // 起始透明度
                mStartAlpha = ViewCompat.getAlpha(alpha_cover);
                break;
            case MotionEvent.ACTION_MOVE:
                // 当前手指位置
                float currentY = event.getY();
                // 划过屏幕的距离
                float offsetY = currentY - mStartY;
                // 屏幕的高度
                int halfScreenH = getWindowManager().getDefaultDisplay().getHeight() / 2;
                // 划过屏幕的百分比
                float movePercent = offsetY / halfScreenH;

                // 屏幕左侧修改亮度，屏幕右侧修改音量
                int halfScreenW = getWindowManager().getDefaultDisplay().getWidth()/2;
                if (event.getX() < halfScreenW){
                    // 左侧
                    moveAlpha(movePercent);

                }else{
                    // 右侧
                    moveVolume(movePercent);
                }
                break;
        }

        return true;
    }

    /** 根据百分比来修改屏幕亮度 */
    private void moveAlpha(float movePercent) {
        // 最终透明度 = 起始透明度 + 划过屏幕的百分比
        float finalAlpha = mStartAlpha + movePercent;
//        logE("VideoPlayerActivity.moveAlpha,finalAlpha="+finalAlpha);
        // 系统没有限制 alpha 的上下限
        if (finalAlpha >=0 && finalAlpha <= 1){
            ViewCompat.setAlpha(alpha_cover, finalAlpha);
        }
    }

    /** 根据百分比来修改系统音量 */
    private void moveVolume(float movePercent) {
        // 变化的音量
        float offsetVolume = movePercent * sk_volume.getMax();
        // 最终音量
        int finalVolume = (int) (mStartVolume + offsetVolume);
//        logE("VideoPlayerActivity.moveVolume,finalVolume="+finalVolume);
        // 更新音量
        updateVolume(finalVolume);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.video_iv_pause:
                switchPauseStatus();
                break;
            case R.id.video_iv_mute:
                switchMuteStatus();
                break;
            case R.id.video_iv_pre:
                playPre();
                break;
            case R.id.video_iv_next:
                playNext();
                break;
            case R.id.video_iv_fullscreen:
                switchFullScreenStatus();
                break;
        }
    }

    /** 切换视频的全屏和默认比例 */
    private void switchFullScreenStatus() {
        videoView.switchFullScreen();

        // 更新全屏按钮
        if (videoView.isFullScreen()){
            // 全屏状态
            iv_fullscreen.setImageResource(R.drawable.video_defaultscreen_selector);
        }else{
            // 默认大小
            iv_fullscreen.setImageResource(R.drawable.video_fullscreen_selector);
        }
    }

    /*播放上一曲*/
    private void playPre() {
        if (position!=0){
            position--;
            playItem();
        }

        updatePreAndNext();
    }

    /*播放下一曲*/
    private void playNext() {
        if (position != videoItems.size()-1){
            position++;
            playItem();
        }
        updatePreAndNext();
    }

    /** 更新下一曲和上一曲使用的图片*/
    private void updatePreAndNext() {
        iv_pre.setEnabled(position!=0);
        iv_next.setEnabled(position != videoItems.size()-1);
    }

    /** 如果当前不是静音状态，保存当前音量，并设置音量为 0；如果当前是静音状态，将音量恢复成之前的值 */
    private void switchMuteStatus() {

        if (getCurrentVolume() != 0){
            mCurrentVolume = getCurrentVolume();
            updateVolume(0);
        }else{
            updateVolume(mCurrentVolume);
        }
    }

    /** 设置系统音量为 volume */
    private void updateVolume(int volume) {

        // index 要设置的音量
        // flag 为 1则显示系统的音量控制条，为 0 则不显示
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume, 0);
        sk_volume.setProgress(volume);
    }

    /** 获取当前音量 */
    private int getCurrentVolume() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /** 切换暂停状态，并更新使用的图片 */
    private void switchPauseStatus() {
        if (videoView.isPlaying()){
            // 正在播放，切换到暂停状态
            videoView.pause();
            mHandler.removeMessages(MSG_UPDATE_POSTITION);
        }else{
            // 暂停状态，切换到播放状态
            videoView.start();
            startUpdatePosition();
        }

        updatePauseBtn();
    }

    /** 根据当前的播放状态，更新暂停按钮的图片 */
    private void updatePauseBtn() {
//        logE("VideoPlayerActivity.updatePauseBtn,videoView.isPlaying()="+videoView.isPlaying());
        if (videoView.isPlaying()){
            // 播放状态，显示暂停按钮
            iv_pause.setImageResource(R.drawable.video_pause_selector);
        }else{
            // 暂停状态，显示正在播放按钮
            iv_pause.setImageResource(R.drawable.video_play_selector);
        }
    }

    /** 更新系统时间，并延迟一段时间后再次更新 */
    private void startUpdateSystemTime() {
//        logE("VideoPlayerActivity.startUpdateSystemTime,time="+System.currentTimeMillis());
        tv_system_time.setText(StringsUtils.formatSystemTime());

        // 发送延迟消息
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_SYSTEM_TIME, 500);
    }

    /** 根据当前电量，更新电池图片 */
    private void updateBatteryPic(int level) {
        if (level < 10) {
            iv_battery.setImageResource(R.mipmap.ic_battery_0);
        } else if (level < 20){
            iv_battery.setImageResource(R.mipmap.ic_battery_10);
        } else if (level < 40){
            iv_battery.setImageResource(R.mipmap.ic_battery_20);
        } else if (level < 60){
            iv_battery.setImageResource(R.mipmap.ic_battery_40);
        } else if (level < 80){
            iv_battery.setImageResource(R.mipmap.ic_battery_60);
        } else if (level < 100){
            iv_battery.setImageResource(R.mipmap.ic_battery_80);
        }else{
            iv_battery.setImageResource(R.mipmap.ic_battery_100);
        }
    }

    /** 更新播放进度，并稍后再次更新*/
    private void startUpdatePosition() {
        int position = videoView.getCurrentPosition();
        updatePosition(position);

        // 发送延迟消息
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_POSTITION, 500);
    }

    /** 更新界面上的进度为 position */
    private void updatePosition(int position) {
//        logE("VideoPlayerActivity.startUpdatePosition,duration="+videoView.getDuration()+",position="+position);
        tv_position.setText(StringsUtils.formatDuration(position));
        sk_position.setProgress(position);
    }

    /** 切换控制面板的显示和隐藏 */
    private void switchControlor() {
        if (isControlorShowing){
            // 显示状态，将面板隐藏
            hideControlor();
        }else{
            // 隐藏状态，将面板显示
            showControlor();

            // 延迟一段时间后将面板隐藏起来
            mHandler.removeMessages(MSG_HIDE_CONTROLOR);
            notifyHideControlor();
        }
    }

    /**显示控制面板*/
    private void showControlor() {
        ViewCompat.animate(ll_top).translationY(0).setDuration(1000).start();
        ViewCompat.animate(ll_bottom).translationY(0).setDuration(1000).start();
        isControlorShowing = true;
    }

    /** 隐藏控制面板*/
    private void hideControlor() {
        ViewCompat.animate(ll_top).translationY(-ll_top.getHeight()).setDuration(1000).start();
        ViewCompat.animate(ll_bottom).translationY(ll_bottom.getHeight()).setDuration(1000).start();
        isControlorShowing = false;
    }

    /** 发送延迟消息，5秒后自动隐藏控制面板 */
    private void notifyHideControlor() {
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLOR,5000);
    }


    private class OnVideoPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            // 资源准备完成，开始播放
            videoView.start();

            // 更新暂停按钮的图片
            updatePauseBtn();

            // 更新播放进度
            int duration = videoView.getDuration();
            tv_duration.setText(StringsUtils.formatDuration(duration));
            sk_position.setMax(duration);

            startUpdatePosition();

            // 隐藏加载遮罩
            loading_cover.setVisibility(View.GONE);

        }
    }
    /**
     * 电量的广播
     */
    private class VideoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)){
                // 获取系统电量
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//                logE("VideoReceiver.onReceive,level="+level);
                updateBatteryPic(level);
            }
        }
    }
    /**
     * 音量与视频播放进度的SeekBars的监听,即滑动SeekBar改变视频播放的进度与音量的大小
     */
    private class OnVideoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        /** 当进度发生变化的时候被调用 */
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            logE("OnVideoSeekBarChangeListener.onProgressChanged,progress="+progress+";fromUser="+fromUser);
            // 不是用户发起的操作，则不处理
            if (!fromUser){
                return;
            }

            switch (seekBar.getId()){
                case R.id.video_sk_volume:
                    updateVolume(progress); //改变音量大小
                    break;
                case R.id.video_sk_position:
                    videoView.seekTo(progress);   //改变视频播放进度
                    break;
            }

        }

        @Override
        /** 当手势按下的时候被调用 */
        public void onStartTrackingTouch(SeekBar seekBar) {
//            logE("OnVideoSeekBarChangeListener.onStartTrackingTouch,");
            mHandler.removeMessages(MSG_HIDE_CONTROLOR);
        }

        @Override
        /** 当手指移开的时候被调用*/
        public void onStopTrackingTouch(SeekBar seekBar) {
//            logE("OnVideoSeekBarChangeListener.onStopTrackingTouch,");
            notifyHideControlor();
        }
    }

    private class OnVideoCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 视频播放结束
            // 由于系统bug，需要强制设置播放进度为视频总时长
            mHandler.removeMessages(MSG_UPDATE_POSTITION);
            updatePosition(videoView.getDuration());

            // 更新暂停按钮的图片
//            updatePauseBtn();
            // 由于系统的 bug，在播放结束后，仍然显示为正在播放状态，只能手动更新图片
            iv_pause.setImageResource(R.drawable.video_play_selector);
        }
    }

    private class OnVideoGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        // 单击
        public boolean onSingleTapConfirmed(MotionEvent e) {
//            logE("OnVideoGestureListener.onSingleTapConfirmed,");
            switchControlor();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        // 双击
        public boolean onDoubleTap(MotionEvent e) {
//            logE("OnVideoGestureListener.onDoubleTap,");
            switchFullScreenStatus();
            return super.onDoubleTap(e);
        }

        @Override
        // 长按
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            switchPauseStatus();
        }
    }

    private class OnVideoBufferingUpdateListener implements MediaPlayer.OnBufferingUpdateListener {
        @Override
        // 当缓冲进度发生变化时会被回调
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            logE("OnVideoBufferingUpdateListener.onBufferingUpdate,percent="+percent);

            float bufferPercent = percent / 100f;
            int secondProgress = (int) (sk_position.getMax() * bufferPercent);
            sk_position.setSecondaryProgress(secondProgress);
        }
    }

    private class OnVideoErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        // 当发生播放错误时的回调
        public boolean onError(MediaPlayer mp, int what, int extra) {

            AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
            builder.setTitle("警告");
            builder.setMessage("不支持播放");
            builder.setPositiveButton("退出播放", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
            return false;
        }
    }

    private class OnVideoInfoListener implements MediaPlayer.OnInfoListener {
        @Override
        // 在播放过程中如果有警告信息，会回调这个方法
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what){
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    // 播放过程中开始缓冲
                    pb_buffering.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    // 播放过程中缓冲结束
                    pb_buffering.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    }
}
