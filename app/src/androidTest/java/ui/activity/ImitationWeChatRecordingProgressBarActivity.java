package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import videodemo.R;
import view.RecoderProgress;

/**
 * http://www.jianshu.com/p/4789348dce74
 * github:https://github.com/maimingliang/RecoderProgress
 * Created by admin on 2017-08-01.
 */

public class ImitationWeChatRecordingProgressBarActivity extends Activity {

    private RecoderProgress recoderProgress;

    private boolean isStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imitationwechatrecordingprogressbar);

        recoderProgress = (RecoderProgress) findViewById(R.id.recodrProgress);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!isStart){
                    recoderProgress.startAnimation();
                }else{
                    recoderProgress.stopAnimation();

                }
                isStart = !isStart;
            }
        });
    }
}
