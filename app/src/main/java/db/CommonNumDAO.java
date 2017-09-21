package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class CommonNumDAO {

	/**
	 * 获取组的数量
	 * 
	 * @param context
	 * @return
	 */
	public static int getGroupCount(Context context) {
		// 数据库的位置
		File DbFile = new File(context.getFilesDir(), "commonnum.db");
		// 参1:数据库的位置
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DbFile.getAbsolutePath(), null,
				SQLiteDatabase.OPEN_READWRITE);
		int size = 0;
		if (db != null) {
			String sql = "select count(*) from classlist";
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor != null) {
				if (cursor.moveToNext()) {
					size = cursor.getInt(0);
				}
				cursor.close();
			}
			db.close();
		}
		return size;
	}

	/**
	 * 获取组的数量
	 * 
	 * @param context
	 * @param groupPosition
	 *            组的索引 从0开始
	 * @return
	 */
	public static int getChildCount(Context context, int groupPosition) {
		// 数据库的位置
		File DbFile = new File(context.getFilesDir(), "commonnum.db");
		// 参1:数据库的位置
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DbFile.getAbsolutePath(), null,
				SQLiteDatabase.OPEN_READWRITE);
		int size = 0;
		if (db != null) {
			String sql = "select count(*) from table" + (groupPosition + 1);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor != null) {
				if (cursor.moveToNext()) {
					size = cursor.getInt(0);
				}
				cursor.close();
			}
			db.close();
		}
		return size;
	}

	/**
	 * 获取组的名称
	 * 
	 * @param context
	 * @param groupPosition
	 *            组的索引 从0开始
	 * @return
	 */
	public static String getGroupName(Context context, int groupPosition) {
		// 数据库的位置
		File DbFile = new File(context.getFilesDir(), "commonnum.db");
		// 参1:数据库的位置
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DbFile.getAbsolutePath(), null,
				SQLiteDatabase.OPEN_READWRITE);
		String name = "";
		if (db != null) {
			String sql = "select name from classlist where idx = ?";
			String[] selectionArgs = new String[] { (groupPosition + 1) + "" };
			Cursor cursor = db.rawQuery(sql, selectionArgs);
			if (cursor != null) {
				if (cursor.moveToNext()) {
					name = cursor.getString(0);
				}
				cursor.close();
			}
			db.close();
		}
		return name;
	}

	/**
	 * 获取孩子的内容
	 * 
	 * @param context
	 * @param groupPosition
	 *            组的索引 从0开始
	 * @return
	 */
	public static String[] getChildName(Context context, int groupPosition,
			int childPosition) {
		// 数据库的位置
		File DbFile = new File(context.getFilesDir(), "commonnum.db");
		// 参1:数据库的位置
		SQLiteDatabase db = SQLiteDatabase.openDatabase(DbFile.getAbsolutePath(), null,
				SQLiteDatabase.OPEN_READWRITE);
		String[] child = new String[2];
		if (db != null) {
			String sql = "select name,number from table" + (groupPosition + 1)
					+ " where _id = ?";
			String[] selectionArgs = new String[] { (childPosition + 1) + "" };
			Cursor cursor = db.rawQuery(sql, selectionArgs);
			if (cursor != null) {
				if (cursor.moveToNext()) {
					child[0] = cursor.getString(0);
					child[1] = cursor.getString(1);
				}
				cursor.close();
			}
			db.close();
		}
		return child;
	}
}
