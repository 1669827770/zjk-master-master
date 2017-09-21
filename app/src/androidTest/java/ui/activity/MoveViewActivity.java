package ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import view.DrawView;
import view.MoveImageView;

/**
 * Created by admin on 2017-04-21.
 */

public class MoveViewActivity extends Activity {


    MoveImageView moveImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过XML布局实现
        //setContentView(R.layout.activity_draw_view);
        //final DrawView mDrawView = (DrawView)findViewById(R.id.drawview);

        //通过代码编程实现
        LinearLayout main = new LinearLayout(this);
        main.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        main.setOrientation(LinearLayout.HORIZONTAL);
        main.setGravity(Gravity.CENTER);

        DrawView mDrawView = new DrawView(this);
        mDrawView.setBackgroundColor(Color.BLUE);
        main.addView(mDrawView);

        setContentView(main);//设置Aictivity 显示View


    }


}
