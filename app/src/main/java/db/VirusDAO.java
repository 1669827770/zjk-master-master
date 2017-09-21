package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class VirusDAO {

	/**
	 * 查毒
	 * 
	 * @param context
	 * @return 
	 */
	public static boolean isVirus(Context context, String md5) {
		// 数据库的位置
		File DbFile = new File(context.getFilesDir(), "antivirus.db");
		// 参1:数据库的位置
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				DbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
		if (db != null) {
			String sql = "select * from datable where md5 = ?";
			String[] selectionArgs = new String[]{md5};
			Cursor cursor = db.rawQuery(sql, selectionArgs );
			if(cursor!=null){
				if(cursor.getCount()>0){
					return true;
				}
				cursor.close();
			}
			db.close();
		}
		return false;
	}
}
