package util;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/12.
 */
public class PhoneLogUtils {

    private static final boolean ENABLE = true;

    public static void d(String tag, String msg) {
        if (ENABLE) {
            Log.d("itcast_"+tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ENABLE) {
            Log.e("itcast_"+tag, msg);
        }
    }

    public static void e(Class cls, String msg) {
        if (ENABLE) {
            Log.e("itcast_"+cls.getSimpleName(), msg);
        }
    }

}
