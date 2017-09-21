package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import videodemo.R;
import view.MoveImageView;

/**
 * Created by admin on 2017-04-21.
 */

public class MoveImageviewActivity extends Activity {


    MoveImageView moveImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        moveImageView = (MoveImageView) findViewById(R.id.ImageView01);


    }

    public boolean onTouchEvent(MotionEvent event) {
        moveImageView.autoMouse(event);
        return false;
    }
}
