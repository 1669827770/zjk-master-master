package ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import bean.AudioItem;
import interfaces.Keys;
import services.AudioPlayService;
import util.StringsUtils;
import videodemo.R;
import view.LyricView;

/**
 * 音频播放器界面
 * Created by dzl on 2016/8/15.
 */
public class AudioPlayerActivity extends Activity implements View.OnClickListener{

    private TextView tv_title;
    private ImageView iv_visual_effect;
    private TextView tv_artist;
    private TextView tv_play_time;
    private SeekBar sb_audio;
    private Button btn_play_mode;
    private Button btn_pre;
    private Button btn_next;
    private Button btn_play;
    private ServiceConnection conn;
    private AudioPlayService playService;
    /** 更新播放时间 */
    private static final int UPDATE_PLAY_TIME = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PLAY_TIME:
                    updatePlayTime();
                    break;
            }
        }
    };
    private LyricView lyricView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        initView();
        initListener();
        initData();
    }




    public void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_visual_effect = (ImageView) findViewById(R.id.iv_visual_effect);
        AnimationDrawable rocketAnimation = (AnimationDrawable) iv_visual_effect.getBackground();
        rocketAnimation.start();    // 开始播放帧动画

        tv_artist = (TextView) findViewById(R.id.tv_artist);
        tv_play_time = (TextView) findViewById(R.id.tv_play_time);
        sb_audio = (SeekBar) findViewById(R.id.sb_audio);
        btn_play_mode = (Button) findViewById(R.id.btn_play_mode);
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_play = (Button) findViewById(R.id.btn_play);

        lyricView = (LyricView) findViewById(R.id.lyric_view);
    }


    public void initListener() {
        btn_play_mode.setOnClickListener(this);
        btn_pre.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        sb_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /** 当SeekBar进度发生改变的时候会执行，fromUser参数指示是否是用户触发的改变 */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    playService.seekTo(progress);   // 让播放器进行快进快退
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void initData() {
        connectService();
    }

    /** 连接Service */
    private void connectService() {
        // 获取从音频列表传过来的数据
        ArrayList<AudioItem> audioItems = (ArrayList<AudioItem>) getIntent().getSerializableExtra(Keys.AUDTIO_ITEMS);
        int currentPosition = getIntent().getIntExtra(Keys.CURRENT_POSITION, -1);

        // 把点击通知栏中的intent的what取出来
        int what = getIntent().getIntExtra(Keys.WHAT, -1);

        Intent intent = new Intent(this, AudioPlayService.class);
        intent.putExtra(Keys.AUDTIO_ITEMS, audioItems);
        intent.putExtra(Keys.CURRENT_POSITION, currentPosition);
        intent.putExtra(Keys.WHAT, what);
        startService(intent);   // 开始服务，Activity退出的时候Service还在。

        // 服务连接器
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 连接成功后
                AudioPlayService.MyBinder myBinder = (AudioPlayService.MyBinder) service;
                playService = myBinder.playService;             // 获取Service的引用
                playService.setUi(AudioPlayerActivity.this);    // 把Activity的引用传给服务

                // 这个时候Service和Activity都拿到了对方的引用，可以互相调用了
                // 在双方都拿到对方使用的时候再播放音乐，避免发生空指针异常
                if (playService.playFlag == AudioPlayService.PLAY_FLAG_OPEN_AUDIO) {
                    playService.openAudio();    // 打开一个音乐进行播放
                } else {
                    updateUI(playService.getCurrentAudioItem());
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, conn, BIND_AUTO_CREATE);    // 绑定服务，因为需要调用Service中的方法，所以需要绑定
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_mode:    // 点击了播放模式按钮
                switchPlayMode();
                break;
            case R.id.btn_pre:          // 点击了上一首按钮
                playService.pre();
                break;
            case R.id.btn_play:         // 点击了播放按钮
                playToggle();
                break;
            case R.id.btn_next:         // 点击了下一首按钮
                playService.next();
                break;
        }
    }


    /** 切换播放模式 */
    private void switchPlayMode() {
        int currentPlayMode = playService.swichPlayMode();
        updatePlayModeButtonBg(currentPlayMode);
    }

    /** 更新播放模式按钮的背景图片 */
    private void updatePlayModeButtonBg(int currentPlayMode) {
        switch (currentPlayMode) {
            case AudioPlayService.PLAY_MODE_ORDER:
                btn_play_mode.setBackgroundResource(R.drawable.selector_btn_playmode_order);
                break;
            case AudioPlayService.PLAY_MODE_SINGLE:
                btn_play_mode.setBackgroundResource(R.drawable.selector_btn_playmode_single);
                break;
            case AudioPlayService.PLAY_MODE_RANDOM:
                btn_play_mode.setBackgroundResource(R.drawable.selector_btn_playmode_random);
                break;
            default:
                throw new RuntimeException("见鬼了，出现了一种未知的播放模式：" + currentPlayMode);
        }
    }

    /** 播放开关 */
    private void playToggle() {
        if (playService.isPlaying()) {
            // 如果音乐正在播放，则暂停
            playService.pause();
        } else {
            // 如果音乐是暂停的，则播放
            playService.start();
        }

        updatePlayButtonBg();
    }

    /** 更新播放按钮的背景图片 */
    private void updatePlayButtonBg() {
        if (playService.isPlaying()) {
            // 如果音乐正在播放，则显示暂停按钮
            btn_play.setBackgroundResource(R.drawable.selector_btn_audio_pause);
        } else {
            // 如果音乐是暂停的，则显示播放按钮
            btn_play.setBackgroundResource(R.drawable.selector_btn_audio_play);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);    // 解绑服务
        handler.removeMessages(UPDATE_PLAY_TIME);   // 移除消息
    }

    /**
     * 更新UI（播放服务那边在准备好可以播音乐的时候会调用这个方法）
     * @param currentAudioItem 当前正在播放的音乐的JavaBean
     */
    public void updateUI(AudioItem currentAudioItem) {
        lyricView.setMusicPath(currentAudioItem.data);
        tv_title.setText(currentAudioItem.title);
        tv_artist.setText(currentAudioItem.artist);
        updatePlayButtonBg();       // 更新播放按钮的背景图片
        updatePlayTime();           // 更新播放时间
        updatePlayModeButtonBg(playService.getCurrentPlayMode());
    }

    /** 更新播放时间 */
    private void updatePlayTime() {
        int currentPosition = playService.getCurrentPosition(); // 获取当前播放位置
        lyricView.setCurrentPosition(currentPosition);

        int duration = playService.getDuration();               // 获取音频的总时长
        String palyTime = StringsUtils.formatDuration(currentPosition) + "/" + StringsUtils.formatDuration(duration);
        tv_play_time.setText(palyTime); // 显示播放时间

        // 更新进度条
        sb_audio.setMax(duration);
        sb_audio.setProgress(currentPosition);

        handler.removeMessages(UPDATE_PLAY_TIME);               // 在发送消息之前先移除，确保只有一个循环
        handler.sendEmptyMessageDelayed(UPDATE_PLAY_TIME, 30);  //  每30毫秒更新一次
    }

    /** 播放服务那边准备释放播放器之前会调用这个方法 */
    public void onReleaseBefore() {
        handler.removeMessages(UPDATE_PLAY_TIME);   // 如果播放器要释放了，则Ui界面就不应该再更新了
    }


}
