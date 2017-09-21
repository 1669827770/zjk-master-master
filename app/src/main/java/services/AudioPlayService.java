package services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Random;

import bean.AudioItem;
import interfaces.Keys;
import ui.activity.AudioPlayerActivity;
import videodemo.R;

/**
 * 播放服务
 * Created by dzl on 2016/8/15.
 */
public class AudioPlayService extends Service {
    private static final String TAG = AudioPlayService.class.getSimpleName();
    /** Ui(Activity) 的引用 */
    private AudioPlayerActivity ui;
    /** 所有的音乐列表 */
    private ArrayList<AudioItem> audioItems;
    /** 当前正在播放的音乐的索引 */
    private int currentPosition;
    /** 播放器 */
    private MediaPlayer mMediaPlayer;
    /** 当前正在播放的音乐的JavaBean */
    private AudioItem currentAudioItem;
    /** 顺序播放模式 */
    public static final int PLAY_MODE_ORDER = 0;
    /** 单曲播放模式 */
    public static final int PLAY_MODE_SINGLE = 1;
    /** 随机播放模式 */
    public static final int PLAY_MODE_RANDOM = 2;
    /** 当前播放模式 */
    public int currentPlayMode;
    private SharedPreferences sp;
    private Random random;
    /** 点击了通知栏中的根容器 */
    public static final int NOTIFICATION_ROOT_CONTAINER = 1;
    /** 点击了通知栏中的上一首按钮 */
    public static final int NOTIFICATION_PRE = 2;
    /** 点击了通知栏中的下一首按钮 */
    public static final int NOTIFICATION_NEXT = 3;
    /** 通知管理器，用于发送和取消通知 */
    private NotificationManager notificationManager;
    /** 通知id  */
    int notificationId = 1;
    public int playFlag;
    /** 打开音乐的标志 */
    public static final int PLAY_FLAG_OPEN_AUDIO = 1;
    /** 更新UI的标志 */
    public static final int PLAY_FLAG_UPDATE_UI = 2;


    /** 当服务第一次创建的时候会执行这个方法 */
    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);   // 获取一个默认的配置文件
        random = new Random();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /** 每次调用startService方法的时候都会执行这个方法  */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 读取持久化的播放模式
        currentPlayMode = sp.getInt(Keys.CURREENT_PLAY_MODE, PLAY_MODE_ORDER);

        // 用于判断是点击了通知栏的哪个按钮，如果不是点击通知栏，则取消不what值
        int what = intent.getIntExtra(Keys.WHAT, -1);
        System.out.println("what = " + what);
        switch (what) {
            case NOTIFICATION_ROOT_CONTAINER:   // 点击了通知栏中的根容器，只需要更新UI
                playFlag = PLAY_FLAG_UPDATE_UI;
                break;
            case NOTIFICATION_PRE:              // 点击了通知栏中上一首按钮
                pre();
                break;
            case NOTIFICATION_NEXT:              // 点击了通知栏中下一首按钮
                next();
                break;
            default:
                // 如果点击的是音乐列表进来的
                // 取出从Activity中传过来的Intent中的数据
                audioItems = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.AUDTIO_ITEMS);
                currentPosition = intent.getIntExtra(Keys.CURRENT_POSITION, -1);
                playFlag = PLAY_FLAG_OPEN_AUDIO;    // 打开音乐进行播放
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /** 打开一个音乐 */
    public void openAudio() {
        if (audioItems == null || audioItems.isEmpty() || currentPosition == -1) {
            return; // 如果没有音频数据，则什么也不做
        }

        // 从列表中取出一个音乐JavaBean
        currentAudioItem = audioItems.get(currentPosition);

        // 在播放音乐之前，先让Ui界面停止更新
        ui.onReleaseBefore();

        release();  // 释放播放器

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setDataSource(currentAudioItem.data);  // 把音乐地址设置给播放器
            mMediaPlayer.prepareAsync();    // 进行异步准备
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** 释放播放器 */
    private void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /** 在绑定服务的时候会执行这个方法 */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /** 开始播放音乐 */
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();   // 开始播放音乐
            sendNotification();
        }
    }

    /** 保存Activity（ui）的引用 */
    public void setUi(AudioPlayerActivity ui) {
        this.ui = ui;
    }

    /** 获取当前播放位置 */
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /** 获取音频的总时长 */
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /** 快进快退 */
    public void seekTo(int postion) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(postion);
        }
    }

    /** 判断音乐当前是否是正在播放 */
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    /** 暂停音乐的播放 */
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            notificationManager.cancel(notificationId); // 取消通知的显示
        }
    }

    /** 播放上一首音乐 */
    public void pre() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:   // 顺序循环播放
                if (currentPosition != 0) {
                    // 如果当前播放的不是第0位置的音乐，则说明还有上一首
                    currentPosition--;
                } else {
                    // 如果当前已经是第0位置的音乐，则播放最后一首
                    currentPosition = audioItems.size() - 1;
                }
                break;
            case PLAY_MODE_SINGLE:  // 单曲播放

                break;
            case PLAY_MODE_RANDOM:  // 随机播放
                currentPosition = random.nextInt(audioItems.size());
                break;
        }

        openAudio();
    }

    /** 播放下一首音乐 */
    public void next() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:   // 顺序循环播放
                if (currentPosition != (audioItems.size() -1)) {
                    // 如果当前播放的不是第最后位置的音乐，则说明还有下一首
                    currentPosition++;
                } else {
                    // 如果当前已经是最后一首了
                    currentPosition = 0;
                }
                break;
            case PLAY_MODE_SINGLE:  // 单曲播放

                break;
            case PLAY_MODE_RANDOM:  // 随机播放
                currentPosition = random.nextInt(audioItems.size());
                break;
        }

        openAudio();
    }

    /** 获取当前正在播放的音乐的JavaBean */
    public AudioItem getCurrentAudioItem() {
        return currentAudioItem;
    }




    public class MyBinder extends Binder {
        public AudioPlayService playService = AudioPlayService.this;
    }

    /** 在解绑服务的时候会执行这个方法 */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /** 当服务销毁的时候会执行这个方法 */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 切换播放模式
     * @return 返回切换之后的模式
     */
    public int swichPlayMode() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:   // 如果当前是顺序播放，则切换为单曲播放
                currentPlayMode = PLAY_MODE_SINGLE;
                break;
            case PLAY_MODE_SINGLE:  // 如果当前是单曲播放，则切换为随机播放
                currentPlayMode = PLAY_MODE_RANDOM;
                break;
            case PLAY_MODE_RANDOM:  // 如果当前是随机播放，则切换为顺序播放
                currentPlayMode = PLAY_MODE_ORDER;
                break;
            default:
                throw new RuntimeException("见鬼了，遇到了一个未知的播放模式：" + currentPlayMode);
        }

        sp.edit().putInt(Keys.CURREENT_PLAY_MODE, currentPlayMode).commit(); // 持久化播放模式

        return currentPlayMode;
    }

    /** 获取当前的播放模式 */
    public int getCurrentPlayMode() {
        return currentPlayMode;
    }

    /** 音乐准备好的监听器 */
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {

        /** 音乐准备好之后会调用这个方法 */
        @Override
        public void onPrepared(MediaPlayer mp) {
            start();
            ui.updateUI(currentAudioItem);  // 通知UI更新界面
        }
    };

    /** 音乐播放完成的监听器 */
    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        /**  当音乐播放完成之后会调用这个方法 */
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    };

    /** 发送通知 */
    public void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_notification)		        // 通知图标
                .setTicker("当前正在播放：" + currentAudioItem.title)		// 通知的提示文本
                .setContent(getRemoteViews())	// 设置通知的布局
                .setOngoing(true);		// 让通知栏左右滑的时候不能取消通知

        notificationManager.notify(notificationId, builder.build());
    }

    /** 通过这个RemoteViews可以自定义通知栏的布局 */
    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tv_title, currentAudioItem.title);
        remoteViews.setTextViewText(R.id.tv_artist, currentAudioItem.artist);
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(NOTIFICATION_ROOT_CONTAINER));
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getServicePendingIntent(NOTIFICATION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getServicePendingIntent(NOTIFICATION_NEXT));
        return remoteViews;
    }

    /**
     * 获取一个可以打开Activity的PendingIntent对象
     * @param what 用于区分点击了不同的按钮
     * @return
     */
    private PendingIntent getActivityPendingIntent(int what) {
        Intent intent = new Intent(this, AudioPlayerActivity.class);	// 指定点击通知栏的时候要打开的Activity
        intent.putExtra(Keys.WHAT, what);
        // PendingIntent 什么时候执行这个意图是不确定
        PendingIntent contentIntent = PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    /**
     * 获取一个可以打开Service的PendingIntent对象
     * @param what 用于区分点击了不同的按钮
     * @return
     */
    private PendingIntent getServicePendingIntent(int what) {
        Intent intent = new Intent(this, AudioPlayService.class);	// 指定点击通知栏的时候要打开的Activity
        intent.putExtra(Keys.WHAT, what);
        // PendingIntent 什么时候执行这个意图是不确定
        PendingIntent contentIntent = PendingIntent.getService(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }
}
