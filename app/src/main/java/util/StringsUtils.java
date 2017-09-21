package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/12.
 */
public class StringsUtils {
    private static final String TAG = "StringUtils";

    /** 将时间值转换为字符串 --> 09:09, 01:01:01*/
    public static String formatDuration(int duration){
        // 创建日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        // 设置时间
//        calendar.setTimeInMillis(duration);
        calendar.add(Calendar.MILLISECOND, duration);
        // 获取时分秒信息
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

//        LogUtils.e(TAG,"StringUtils.formatDuration,hour="+hour+";min="+min+";sec="+sec);
        if (hour < 1){
            // 09:09
            return String.format("%02d:%02d",min,sec);
        }else{
            // 01:01:01
            return String.format("%02d:%02d:%02d",hour,min,sec);
        }
    }

    /** 格式化系统时间，01:01:01 */
    public static String formatSystemTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(new Date());
    }
}
