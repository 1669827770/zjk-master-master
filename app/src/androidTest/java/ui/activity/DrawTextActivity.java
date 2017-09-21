package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import view.DrawTextTestView;

import static base.MyApplication.mContext;

/**
 * Created by admin on 2017-07-10.
 */

public class DrawTextActivity extends Activity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawTextTestView drawTextTestView = new DrawTextTestView(mContext);
        setContentView(drawTextTestView);
    }
}
