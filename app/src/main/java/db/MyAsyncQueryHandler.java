package db;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

/**
 * Created by Administrator on 2016/8/12.
 */
public class MyAsyncQueryHandler extends AsyncQueryHandler {

    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    // 查询结束，在主线程返回数据
    // token 可以用于区分不同的查询类型
    // cookie 接受界面上传递的任意对象
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        CursorAdapter adapter = (CursorAdapter) cookie;
        adapter.swapCursor(cursor);
    }
}
