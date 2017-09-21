package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import videodemo.R;

/**
 * Created by admin on 2017-07-21.
 */

public class TimerActivity extends Activity implements View.OnClickListener {


    private Chronometer mChronometer;
    /**
     * 开始计时
     */
    private Button mBtnStarttimer;
    /**
     * 结束计时
     */
    private Button mBtnStoptimer;
    /**
     * 计时器清零
     */
    private Button mBtnZero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_timer);
        initView();


    }


    private void initView() {
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mBtnStarttimer = (Button) findViewById(R.id.btn_starttimer);
        mBtnStarttimer.setOnClickListener(this);
        mBtnStoptimer = (Button) findViewById(R.id.btn_stoptimer);
        mBtnStoptimer.setOnClickListener(this);
        mBtnZero = (Button) findViewById(R.id.btn_zero);
        mBtnZero.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_starttimer:
                // 将计时器清零
                mChronometer.setBase(SystemClock.elapsedRealtime());
                //开始计时
                mChronometer.start();
                break;
            case R.id.btn_stoptimer:
                //停止计时
                mChronometer.stop();
                break;
            case R.id.btn_zero:
                // 将计时器清零
                mChronometer.setBase(SystemClock.elapsedRealtime());
                break;
        }
    }
}
