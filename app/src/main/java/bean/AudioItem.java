package bean;

import android.database.Cursor;
import android.provider.MediaStore.Audio.Media;

import java.io.Serializable;

/**
 * Created by dzl on 2016/8/15.
 */
public class AudioItem implements Serializable {

    // Media._ID, Media.TITLE, Media.ARTIST, Media.DATA
    /** 音乐标题 */
    public String title;
    /** 音乐艺术家（歌手名字） */
    public String artist;
    /** 音乐路径 */
    public String data;


    /** 把一个Cursor解析成一个JavaBean */
    public static AudioItem parseCursor(Cursor cursor) {
        AudioItem item = new AudioItem();
        item.title = cursor.getString(1);
        item.artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST));
        item.data = cursor.getString(cursor.getColumnIndex(Media.DATA));
        return item;
    }
}
