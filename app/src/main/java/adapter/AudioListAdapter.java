package adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bean.AudioItem;
import videodemo.R;


/**
 * Created by Administrator on 2016/8/12.
 */
public class AudioListAdapter extends CursorAdapter {

    public AudioListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public AudioListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    // 创建新View
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.audiolist_item, null);
        ViewHolder holder = new ViewHolder();

        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.tv_artist = (TextView) view.findViewById(R.id.tv_artist);

        view.setTag(holder);
        return view;
    }

    @Override
    // 填充内容
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // 将cursor数据解析成bean对象
        AudioItem audioItem = AudioItem.parseCursor(cursor);

        // 填充数据
        holder.tv_title.setText(audioItem.title);       // 显示音乐标题
        holder.tv_artist.setText(audioItem.artist);     // 显示音乐艺术家(歌手名字)
    }

    private class ViewHolder {
        /** 音乐标题 */
        TextView tv_title;
        /** 音乐艺术家(歌手名字) */
        TextView tv_artist;
    }
}
