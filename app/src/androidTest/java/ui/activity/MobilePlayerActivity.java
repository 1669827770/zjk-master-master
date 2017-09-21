package ui.activity;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.VideoListAdapter;
import bean.VideoItem;
import db.MyAsyncQueryHandler;
import videodemo.R;

/**
 * Created by admin on 2017-07-19.
 */

public class MobilePlayerActivity extends Activity {

    private ListView mSimpleListview;
    private VideoListAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobileplayer);
        initView();
        initListener();
        initData();

    }

    private void initData() {
        // 从系统的 MediaProvider 查询视频信息
        ContentResolver resolver = getApplication().getContentResolver();
//        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{Media._ID/*"uid as _id"*/,Media.DATA, Media.SIZE, Media.DURATION, Media.TITLE}, null, null, null);
//
////        CursorUtils.printCursor(cursor);
//        // 更新 cursor数据
//        mAdapter.swapCursor(cursor);

        // 开启子线程查询
        AsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(resolver);
        asyncQueryHandler.startQuery( 0 , mAdapter  , MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Video.Media._ID/*"uid as _id"*/, MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.TITLE}, null, null, null);

    }

    private void initListener() {

        mSimpleListview.setOnItemClickListener(new OnVideoItemClickListener());

        mAdapter = new VideoListAdapter(getApplication(),null);
        mSimpleListview.setAdapter(mAdapter);
    }

    private void initView() {
        mSimpleListview = (ListView) findViewById(R.id.simple_listview);
    }

    private class OnVideoItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 获取被选中条目的数据
            Cursor cursor = (Cursor) mAdapter.getItem(position);
//            VideoItem videoItem = VideoItem.parseCursor(cursor);
            ArrayList<VideoItem> videoItems = VideoItem.parseListFromCursor(cursor);

            // 跳转到视频播放界面
            Intent intent = new Intent(MobilePlayerActivity.this, VideoPlayerActivity.class);
//            Intent intent = new Intent(getActivity(), VitamioPlayerActivity.class);
//            intent.putExtra("videoItem",videoItem);
            intent.putExtra("videoItems",videoItems);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }



}
