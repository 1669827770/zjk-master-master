package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import view.CanvasTestView;

import static base.MyApplication.mContext;

/**
 * Created by admin on 2017-07-10.
 */

public class CanvasActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CanvasTestView canvasTestView = new CanvasTestView(mContext);
        setContentView(canvasTestView);
    }
}
