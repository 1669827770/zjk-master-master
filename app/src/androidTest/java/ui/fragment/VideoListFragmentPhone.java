package ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import ui.PhoneBaseFragment;
import videodemo.R;

/**
 * Created by Administrator on 2016/8/12.
 */
public class VideoListFragmentPhone extends PhoneBaseFragment {

//    private ListView listView;
//    private VideoListAdapter mAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void initView() {
//        listView = (ListView) findViewById(R.id.simple_listview);
    }

    @Override
    public void initListener() {
//        listView.setOnItemClickListener(new OnVideoItemClickListener());
//
//        mAdapter = new VideoListAdapter(getActivity(),null);
//        listView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        // 从系统的 MediaProvider 查询视频信息
//        ContentResolver resolver = getActivity().getContentResolver();
////        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{Media._ID/*"uid as _id"*/,Media.DATA, Media.SIZE, Media.DURATION, Media.TITLE}, null, null, null);
////
//////        CursorUtils.printCursor(cursor);
////        // 更新 cursor数据
////        mAdapter.swapCursor(cursor);
//
//        // 开启子线程查询
//        AsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(resolver);
//        asyncQueryHandler.startQuery( 0 , mAdapter  , Media.EXTERNAL_CONTENT_URI, new String[]{Media._ID/*"uid as _id"*/, Media.DATA, Media.SIZE, Media.DURATION, Media.TITLE}, null, null, null);
    }

    @Override
    public void processClick(View view) {

    }

    private class OnVideoItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 获取被选中条目的数据
//            Cursor cursor = (Cursor) mAdapter.getItem(position);
////            VideoItem videoItem = VideoItem.parseCursor(cursor);
//            ArrayList<VideoItem> videoItems = VideoItem.parseListFromCursor(cursor);
//
//            // 跳转到视频播放界面
//            Intent intent = new Intent(getActivity(), VideoPlayerActivityPhone.class);
////            Intent intent = new Intent(getActivity(), VitamioPlayerActivity.class);
////            intent.putExtra("videoItem",videoItem);
//            intent.putExtra("videoItems",videoItems);
//            intent.putExtra("position",position);
//            startActivity(intent);
        }
    }
}
