package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import videodemo.R;
import view.SettingItemView;

/**
 * Created by admin on 2017-07-03.
 */

public class CustomCombinationsActivity extends Activity implements View.OnClickListener {

    private SettingItemView mSivAutoUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customcombinations);
        initView();
    }


    private void initView() {
        mSivAutoUpdate = (SettingItemView) findViewById(R.id.siv_auto_update);
        mSivAutoUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siv_auto_update:
                mSivAutoUpdate.toggle();// 切换开关状态
                break;
        }
    }
}
