package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import videodemo.R;
import view.MyToggleButton;

/**
 * Created by admin on 2017-07-07.
 */

public class ToggleButtonViewActivity extends Activity {

    private MyToggleButton myToggleButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_togglebuttonview);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        myToggleButton = (MyToggleButton) findViewById(R.id.mytogglebutton);
        //将要设置的图片传递给自定义控件显示
		/*myToggleButton.setBackgroundAndIcon(R.drawable.slide_background, R.drawable.slide_icon);
		myToggleButton.setState(true);*/


        myToggleButton.setOnToggleListener(new MyToggleButton.onToggleListener() {

            @Override
            public void toggle(boolean isOpen) {
                //监听了滑动事件才能出现吐司
                Toast.makeText(getApplicationContext(), isOpen ? "开启" : "关闭", Toast.LENGTH_SHORT).show();
            }
        });

    }

}