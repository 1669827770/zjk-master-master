package ui.activity.phoneplayer;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import ui.PhoneBaseActivity;
import videodemo.R;


/**
 * Created by Administrator on 2016/8/12.
 */
public class SplashActivityPhone extends PhoneBaseActivity {
    /**
     * 返回当前 Activity 使用的布局
     */
    @Override
    public int getLayoutID() {
        return R.layout.activity_splashphone;
    }

    /**
     * 所有的 findViewById 操作必须在这个方法处理
     */
    @Override
    public void initView() {

    }

    /**
     * 注册监听器、适配器、广播接收者
     */
    @Override
    public void initListener() {

    }

    /**
     * 获取数据，初始化界面
     */
    @Override
    public void initData() {

    }

    /**
     * 在Base没有处理的点击事件，在这个方法统一处理
     *
     * @param view
     */
    @Override
    public void processClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        // 延迟跳转
        // 使用子线程休眠
//        new  Thread(){
//            @Override
//            public void run() {
//                SystemClock.sleep(2000);
//
//                startMainActivity();
//            }
//        }.start();

        // 使用 Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 100);
    }

    /** 跳转到主界面 */
    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivityPhone.class);
        startActivity(intent);

        finish();
    }
}
