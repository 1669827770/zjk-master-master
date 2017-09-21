package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtilsOne {

	private static SharedPreferences sp;

	public static void getSp(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
	}

	public static void putParam(Context context, String paramName, int value) {
		getSp(context);
		Editor editor = sp.edit();
		editor.putInt(paramName, value);
		editor.commit();
	}

	public static void putParam(Context context, String paramName, boolean value) {
		getSp(context);
		Editor editor = sp.edit();
		editor.putBoolean(paramName, value);
		editor.commit();
	}

	public static void putParam(Context context, String paramName, String value) {
		getSp(context);

		Editor editor = sp.edit();
		editor.putString(paramName, value);
		editor.commit();
	}

	public static int getIntParam(Context context, String paramName, int def) {
		getSp(context);
		return sp.getInt(paramName, def);
	}

	public static int getIntParam(Context context, String paramName) {
		return getIntParam(context, paramName, 0);
	}

	public static boolean getBooleanParam(Context context, String paramName,
			boolean def) {
		getSp(context);
		return sp.getBoolean(paramName, def);
	}

	public static boolean getBooleanParam(Context context, String paramName) {
		return getBooleanParam(context, paramName, false);
	}

	public static String getStringParam(Context context, String paramName) {
		getSp(context);
		return sp.getString(paramName, null);
	}

}
