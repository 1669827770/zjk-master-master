package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import view.PathTestView;

import static base.MyApplication.mContext;

/**
 * Created by admin on 2017-07-10.
 */

public class BesselPlotActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PathTestView pathTestView = new PathTestView(mContext);
        setContentView(pathTestView);
    }
}
