package ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ScrollView;
import android.widget.TextView;

import view.OneFlowLayout;

/**
 * Created by admin on 2017-07-04.
 */

public class OneViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);	// 创建ScrollView
        OneFlowLayout flowLayout = new OneFlowLayout(this);	// 创建FlowLayout
        TextView textView = new TextView(this);		// 创建TextView
        textView.setText("一个View,智慧，城市");
        textView.setTextSize(32);
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);

        flowLayout.addView(textView);		// 把TextView装到FlowLayout
        scrollView.addView(flowLayout);	// 把FlowLayout装到ScrollView中
        setContentView(scrollView);		// 所ScrollView设置为显示界面

    }
}
