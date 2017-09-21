package ui.fragment;

import android.view.View;

import ui.PhoneBaseFragment;
import videodemo.R;

/**
 * 音乐列表模块
 * Created by Administrator on 2016/8/12.
 */
public class AudioListFragmentPhone extends PhoneBaseFragment {

//    private ListView listView;
//    private AudioListAdapter adapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_audio_list;
    }

    @Override
    public void initView() {
//        listView = (ListView) findViewById(R.id.simple_listview);
//        adapter = new AudioListAdapter(getActivity(), null);
//        listView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cursor = (Cursor) parent.getItemAtPosition(position);// 取出点击的item对应的数据
//                Intent intent = new Intent(getActivity(), AudioPlayerActivityPhone.class);
//                intent.putExtra(Keys.CURRENT_POSITION, position);           // 存入当前点击item的位置
//                intent.putExtra(Keys.AUDTIO_ITEMS, getAudioItems(cursor));  // 存入所有的音频数据列表
//                startActivity(intent);  // 跳转到播放器界面
//            }
//        });
    }

    /** 把Cursor中所有的记录读取出来，保存到集合中 */
//    private ArrayList<AudioItem> getAudioItems(Cursor cursor) {
//        ArrayList<AudioItem> items = new ArrayList<>();
//        cursor.moveToPosition(-1);      // 把游标移动到-1的位置
//        while (cursor.moveToNext()) {   // 如果能移动到下一条数据
//            AudioItem item = AudioItem.parseCursor(cursor);
//            items.add(item);
//        }
//        return items;
//    }

    @Override
    public void initData() {
//        ContentResolver resolver = getActivity().getContentResolver();
//        MyAsyncQueryHandler queryHandler = new MyAsyncQueryHandler(resolver);
//        int token = 0;          // 相当于Message.what
//        Object cookie = adapter;// 相当于Message.obj
//        Uri uri = Media.EXTERNAL_CONTENT_URI;   // 指定要查询的是外部存储的音频
//        String[] projection = { // 指定要查询的列
//                Media._ID, Media.TITLE, Media.ARTIST, Media.DATA
//        };
//        String selection = null;        // 指定查询条件 (where age=? and gender=?)
//        String[] selectionArgs = null;  // 指定查询条件中的参数
//        String orderBy = Media.TITLE + " ASC";  // 指定对Title进行升序的排序
//
//        // 这个查询方法会运行在子线程
//        queryHandler.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
    }

    @Override
    public void processClick(View view) {

    }
}
