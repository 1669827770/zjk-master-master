package view;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.ArrayList;

import ui.activity.video.SingleCallback;
import util.ScreenUtils;
import util.TrimVideoUtil;
import util.UiThreadExecutor;
import videodemo.R;

import static base.MyApplication.mContext;

public class VideoTrimmerView extends FrameLayout {

    /**
     * 计算公式:
     * PixRangeMax = (视频总长 * SCREEN_WIDTH) / 视频最长的裁剪时间(15s)
     * 视频总长/PixRangeMax = 当前视频的时间/游标当前所在位置
     */

    /**
     * 控件
     */
    private VideoView vv_trimvideo;
    private RelativeLayout rl_trimcontainer;
    private RecyclerView rl_trimpictureRecycleview;
    private ImageView iv_leftbar;
    private ImageView iv_rightbar;

    ArrayList<Bitmap> bitmaps;
    HomeAdapter homeAdapter;

    /**
     * 左右手柄按下的坐标
     */
    private int  lastLeftX;
    private int  lastRightX;
    /**
     * 视频播放进度
     */
    private SeekBar sk_progressbar;

    /**
     * 处理Handle的常量，根据视频播放的进度，更新SeekBar的滑动位置
     */
    private static final int MSG_UPDATE_SYSTEM_TIME = 0;
    private static final int MSG_UPDATE_POSTITION = 1;
    private static final int MSG_HIDE_CONTROLOR = 2;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_SYSTEM_TIME:
//                    startUpdateSystemTime();
                    break;
                case MSG_UPDATE_POSTITION:
                    startUpdatePosition();
                    break;
                case MSG_HIDE_CONTROLOR:
//                    hideControlor();
                    break;
            }
        }
    };


    public VideoTrimmerView(Context context) {
        this(context, null);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initlistener();
    }


    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.video_trimmer_view, this, true);
        vv_trimvideo = findViewById(R.id.vv_trimvideo);
        rl_trimcontainer = findViewById(R.id.rl_trimcontainer);
        rl_trimpictureRecycleview = findViewById(R.id.rl_trimpictureRecycleview);
        iv_leftbar = findViewById(R.id.iv_leftbar);
        iv_rightbar = findViewById(R.id.iv_rightbar);
        sk_progressbar = findViewById(R.id.sk_progressbar);

        /**
         * 存放缩略图的集合
         */
        bitmaps = new ArrayList<>();

        /**
         * 缩略图的recycleview
         */
        homeAdapter = new HomeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_trimpictureRecycleview.setLayoutManager(linearLayoutManager);
        rl_trimpictureRecycleview.setAdapter(homeAdapter);


        /**
         * 左手柄的滑动
         */
        iv_leftbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int X = (int) motionEvent.getRawX();

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        lastLeftX = (int) motionEvent.getRawX();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) motionEvent.getRawX() - lastLeftX;
                        int lBarleft = iv_leftbar.getLeft() + dx;
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_leftbar
                                .getLayoutParams();

//-------------------------------------------------------------------
                        /**
                         * 下面这几行代码用于控制两个手柄最短的距离的，其实没必要判断左手柄一定再右手柄的左边，只有有距离了
                         * 就说明在左边
                         */
                        RelativeLayout.LayoutParams rightlayoutParams = (RelativeLayout.LayoutParams) iv_rightbar
                                .getLayoutParams();
                        int leftMargin = rightlayoutParams.leftMargin;

                        if(leftMargin-lBarleft>120 && lBarleft>0){   //大于0是为了防止左手柄滑出界面
                            layoutParams.leftMargin =lBarleft;
                        }
//-------------------------------------------------------------------
                        view.setLayoutParams(layoutParams);
                        lastLeftX = (int) motionEvent.getRawX();
                        break;
                }
                iv_leftbar.invalidate();
                return true;
            }
        });

         /**
          * 右手柄的滑动
          */
        iv_rightbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int X = (int) motionEvent.getRawX();

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        lastRightX = (int) motionEvent.getRawX();
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) motionEvent.getRawX() - lastRightX;
                        int rBarleft = iv_rightbar.getLeft() + dx;

                        RelativeLayout.LayoutParams rightlayoutParams = (RelativeLayout.LayoutParams) iv_rightbar.getLayoutParams();
                        int screenWidth = ScreenUtils.getScreenWidth();

//-------------------------------------------------------------------
                        /**
                         * 下面这几行代码用于控制两个手柄最短的距离的，其实没必要判断左手柄一定再右手柄的左边，只有有距离了
                         * 就说明在左边
                         */
                        RelativeLayout.LayoutParams leftlayoutParams = (RelativeLayout.LayoutParams) iv_leftbar
                                .getLayoutParams();
                        int leftMargin = leftlayoutParams.leftMargin;
                        if(rBarleft-leftMargin>120 && rBarleft<=screenWidth){ //小于等于屏幕宽度是为了防止右手柄滑出界面
                            rightlayoutParams.leftMargin =rBarleft;
                        }
//-------------------------------------------------------------------
                        view.setLayoutParams(rightlayoutParams);
                        lastRightX = (int) motionEvent.getRawX();
                        break;
                }
                iv_rightbar.invalidate();
                return true;
            }
        });

    }

    private void initlistener() {
        sk_progressbar.setOnSeekBarChangeListener(new OnVideoSeekBarChangeListener());

        /**
         *  注册视频监听
         */
        /**
         *  注册视频监听    1.准备，2完成
         */
        vv_trimvideo.setOnPreparedListener(new OnVideoPreparedListener());
        vv_trimvideo.setOnCompletionListener(new OnVideoCompletionListener());
    }


    public void setVideoPath(String videoPath) {

        placevideo(videoPath);

        /**
        *   获取视频时长
        */
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String time = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取视频时长

        Long aLong = Long.valueOf(time);
        Log.d("0000000000000000000000", "[" + time + "]");


        int screenWidth = ScreenUtils.getScreenWidth();
        int i = (int) (screenWidth * (11/20f));


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_rightbar
                .getLayoutParams();

        layoutParams.leftMargin =i;
        iv_rightbar.setLayoutParams(layoutParams);
        iv_rightbar.invalidate();



        /**
         * 将String路径转为URi类型的
         */
        Uri pathUri = Uri.parse(videoPath);


        /**
         * 重点来了就是剪切视频的工具类
         */
        TrimVideoUtil.backgroundShootVideoThumb(1,mContext, pathUri, new SingleCallback<ArrayList<Bitmap>, Integer>() {
            @Override
            public void onSingleCallback(final ArrayList<Bitmap> bitmap, final Integer interval) {
                UiThreadExecutor.runTask("", new Runnable() {
                    @Override
                    public void run() {
                        //讲补获到的截屏添加到listview的Adapter中
                        bitmaps.addAll(bitmap);
                        homeAdapter.notifyDataSetChanged();

                    }
                }, 0L);

            }
        });

    }

    /**
     * 播放视频
     * @param videoPath
     */
    public void placevideo(String videoPath) {
        //设置视频路径
        vv_trimvideo.setVideoURI(Uri.parse(videoPath));
        //开始播放视频
        vv_trimvideo.start();

    }


//
//    int lastX;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("333333333", ""+"999999999999");
//        int action = event.getAction();
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN: {
//                lastX = (int) event.getRawX();
//
//                return true;
//            }
//            case MotionEvent.ACTION_UP: {
//
//                return true;
//            }
//
//            case MotionEvent.ACTION_MOVE: {
//                    int dx = (int) event.getRawX() - lastX;
//                    int left = iv_leftbar.getLeft() + dx;
//                    Log.e("333333333", ""+left);
//                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_leftbar
//                            .getLayoutParams();
////                layoutParams.height=IMAGE_SIZE;
////                layoutParams.width = IMAGE_SIZE;
//                    layoutParams.leftMargin =left;
//                    iv_leftbar.setLayoutParams(layoutParams);
//                iv_leftbar.invalidate();
//                return true;
//            }
//
//            }
//        invalidate();
//        return false;
//    }

    //    int lastX;
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//
//            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//                case MotionEvent.ACTION_DOWN:
//                    lastX = (int) motionEvent.getRawX();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    int dx = (int) motionEvent.getRawX() - lastX;
//                    int left = iv_leftbar.getLeft() + dx;
//                    Log.e("333333333", ""+left);
//                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_leftbar
//                            .getLayoutParams();
////                layoutParams.height=IMAGE_SIZE;
////                layoutParams.width = IMAGE_SIZE;
//                    layoutParams.leftMargin =left;
//                    iv_leftbar.setLayoutParams(layoutParams);
//                    break;
//            }
//        iv_leftbar.invalidate();
//            return true;
//
//    }


    /**
     * 音量与视频播放进度的SeekBars的监听,即滑动SeekBar改变视频播放的进度与音量的大小
     */
    private class OnVideoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        /** 当进度发生变化的时候被调用 */
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            logE("OnVideoSeekBarChangeListener.onProgressChanged,progress="+progress+";fromUser="+fromUser);
            // 不是用户发起的操作，则不处理，
            if (!fromUser){
                return;
            }

            switch (seekBar.getId()){
                case R.id.sk_progressbar:
                    vv_trimvideo.seekTo(progress);   //改变视频播放进度
                    break;
            }

        }

        @Override
        /** 当手势按下的时候被调用 */
        public void onStartTrackingTouch(SeekBar seekBar) {
//            logE("OnVideoSeekBarChangeListener.onStartTrackingTouch,");
//            mHandler.removeMessages(MSG_HIDE_CONTROLOR);
        }

        @Override
        /** 当手指移开的时候被调用*/
        public void onStopTrackingTouch(SeekBar seekBar) {
//            logE("OnVideoSeekBarChangeListener.onStopTrackingTouch,");
//            notifyHideControlor();
        }
    }

    private class OnVideoPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            // 资源准备完成，开始播放
            vv_trimvideo.start();
            // 更新播放进度
            int duration = vv_trimvideo.getDuration();
            sk_progressbar.setMax(duration);

            startUpdatePosition();//获取视频播放进度，更新SeekBar
        }
    }

    /** 获取视频播放进度，更新SeekBar*/
    private void startUpdatePosition() {
        int position = vv_trimvideo.getCurrentPosition();
        sk_progressbar.setProgress(position);
        // 发送延迟消息
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_POSTITION, 500);
    }



    public class OnVideoCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 视频播放结束
            // 由于系统bug，需要强制设置播放进度为视频总时长
            sk_progressbar.setProgress(vv_trimvideo.getDuration());

        }
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_videoscreenshot, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {

            holder.img_screenshot.setImageBitmap(bitmaps.get(position));
        }

        @Override
        public int getItemCount()
        {
            return bitmaps.size();

        }



        class MyViewHolder extends RecyclerView.ViewHolder
        {
            ImageView img_screenshot;

            public MyViewHolder(View view)
            {
                super(view);
                img_screenshot = (ImageView) view.findViewById(R.id.img_screenshot);
            }
        }
    }


}
