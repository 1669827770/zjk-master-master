package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import videodemo.R;
import view.HorizontalProgressBar;

/**
 * Created by admin on 2017-07-13.
 */

public class HorizontalProgressBarActivity extends Activity implements View.OnClickListener {

    private HorizontalProgressBar mHorizontalProgressView;
    /**
     * 启动动画
     */
    private Button mStartAnimationBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalprogressbar);
        initView();

    }

    private void initView() {
        mHorizontalProgressView = (HorizontalProgressBar) findViewById(R.id.horizontal_progress_view);
        mStartAnimationBtn = (Button) findViewById(R.id.startAnimationBtn);
        mStartAnimationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startAnimationBtn:
                mHorizontalProgressView.setProgressWithAnimation(60);
                break;
        }
    }
}
