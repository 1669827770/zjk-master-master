package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This Service is Persistent Service. Do some what you want to do here.<br/>
 *
 * Created by Mars on 12/24/15.
 */
public class Service1 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("SupportService", "Start SupportService");
//        long deviceId = PreferenceUtils.getLong(this, Constant.PREFERENCE_KEY_SUPPORT_BUNDLE_KEY_DEVICE_ID,0);
//        Log.i("SupportService", "deviceId = " + deviceId);
//        com.bidostar.support.protocol.util.PreferenceUtils.setLong(this, com.bidostar.support.protocol.util.Constant.SP_KEY_DEVICE_ID, deviceId); //保存deviceId
//        try {
//            TCPManager.getInstance(this);
//            EventManager.getInstance(this);
//        } catch (IllegalMessageException e) {
//            Log.e("SupportService", e.getMessage());
//            e.printStackTrace();
//        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
//        EventManager eventManager = EventManager.getInstance(this);
//        eventManager.stopAll();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
