package adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bean.VideoItem;
import util.StringsUtils;
import videodemo.R;


/**
 * Created by Administrator on 2016/8/12.
 */
public class VideoListAdapter extends CursorAdapter {

    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public VideoListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    // 创建新View
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.videolist_item, null);
        ViewHolder holder = new ViewHolder();

        holder.tv_title = (TextView) view.findViewById(R.id.videolist_item_tv_title);
        holder.tv_duration = (TextView) view.findViewById(R.id.videolist_item_tv_duration);
        holder.tv_size = (TextView) view.findViewById(R.id.videolist_item_tv_size);

        view.setTag(holder);
        return view;
    }

    @Override
    // 填充内容
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // 将cursor数据解析成bean对象
        VideoItem videoItem = VideoItem.parseCursor(cursor);

        // 填充数据
        holder.tv_title.setText(videoItem.getTitle());
        holder.tv_duration.setText(StringsUtils.formatDuration(videoItem.getDuration()));
        holder.tv_size.setText(Formatter.formatFileSize(context,videoItem.getSize()));

//        holder.tv_title.setText(cursor.getString(cursor.getColumnIndex(Media.TITLE)));
    }

    private class ViewHolder {
        TextView tv_title, tv_duration, tv_size;
    }
}
