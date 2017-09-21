package util;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Activity管理类 后面有一个Util，系统类有一个就是ActivityManager
 * @author http://blog.csdn.net/dinghqalex 
 * 
 */  
public class ActivityManagerUtil {
    /** 
     * 存放Activity的map 
     */  
    private static Map<String, Activity> activitys = new HashMap<String, Activity>();
  
    /** 
     * 获取管理类中注册的所有Activity的map 
     * @return 
     */  
    public static Map<String, Activity> getActivitys()  
    {  
        return activitys;  
    }  
  
    /** 
     * 根据键值取对应的Activity 
     * @param key 键值 
     * @return 键值对应的Activity 
     */  
    public static Activity getActivity(String key)  
    {  
        return activitys.get(key);  
    }  
      
    /** 
     * 注册Activity 
     * @param value 
     * @param key 
     */  
    public static void addActivity(Activity value,String key)  
    {  
        activitys.put(key, value);  
    }  
      
    /** 
     * 将key对应的Activity移除掉 
     * @param key 
     */  
    public static void removeActivity(String key)  
    {  
        activitys.remove(key);  
    }  
      
    /** 
     * finish掉所有的Activity移除所有的Activity 
     */  
    public static void removeAllActivity()  
    {  
        Iterator<Activity> iterActivity = activitys.values().iterator();
        while(iterActivity.hasNext()){  
            iterActivity.next().finish();  
        }  
        activitys.clear();  
    }  
}  