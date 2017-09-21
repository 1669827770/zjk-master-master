package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import videodemo.R;
import view.ProgDescView;

/**
 * Created by admin on 2017-07-04.
 */

public class CustomCombinationsPRogressbarActivity extends Activity {


    private ProgDescView mPdvPmPnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcombinationsprogressbar);
        initView();
    }

    private void initView() {
        mPdvPmPnum = (ProgDescView) findViewById(R.id.pdv_pm_pnum);
        mPdvPmPnum.setLeftText("正在运行" + 8 + "个");
        // 所有进程数量
//        int allProNum = ProcessInfoProvider
//                .getAllProNum(getApplicationContext());
        mPdvPmPnum.setRightText("可有进程" + 10 + "个");
        mPdvPmPnum.setProgress((int) (8 * 100.0f / 10 + 0.5f));
    }
}
