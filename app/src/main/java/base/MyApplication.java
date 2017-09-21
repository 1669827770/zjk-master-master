package base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-05-17.
 */

public class MyApplication extends Application {

    public static Context mContext;
    private List<Activity> mActivityList = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "5ade1968fe", false);
        //初始化全局上下文
        mContext = getApplicationContext();


    }

    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }
    public void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }
}
