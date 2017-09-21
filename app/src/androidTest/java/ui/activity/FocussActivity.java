package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import videodemo.R;

/**
 * Created by admin on 2017-06-05.
 */

public class FocussActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_focus);
    }
}
