package bean;

import android.database.Cursor;
import android.provider.MediaStore.Video.Media;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/12.
 */
public class VideoItem implements Serializable {

    private String title;
    private String path;
    private int size;
    private int duration;

    /** 从 cursor 当前行数据解析出一个对象 */
    public static VideoItem parseCursor(Cursor cursor){
        VideoItem videoItem = new VideoItem();
        if (cursor == null||cursor.getCount()==0){
            return videoItem;
        }

        videoItem.title = cursor.getString(cursor.getColumnIndex(Media.TITLE));
        videoItem.path = cursor.getString(cursor.getColumnIndex(Media.DATA));
        videoItem.size = cursor.getInt(cursor.getColumnIndex(Media.SIZE));
        videoItem.duration = cursor.getInt(cursor.getColumnIndex(Media.DURATION));

        return videoItem;
    }

    /** 从 cursor 里解析出完整的播放列表 */
    public static ArrayList<VideoItem> parseListFromCursor(Cursor cursor) {
        ArrayList<VideoItem> videoItems = new ArrayList<>();
        if (cursor == null|| cursor.getCount()==0){
            return videoItems;
        }

        // 移动光标到列表头部，再解析数据
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            VideoItem item = parseCursor(cursor);
            videoItems.add(item);
        }
        return videoItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                '}';
    }
}
