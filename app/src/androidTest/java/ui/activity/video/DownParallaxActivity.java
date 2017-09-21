package ui.activity.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import util.Cheeses;
import videodemo.R;
import view.ParallaxEffect;

/**
 * Created by admin on 2017-05-15.
 */

public class DownParallaxActivity extends Activity{


    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydownparallax);
        //找到视差特效的ListView，设置适配器
        ParallaxEffect parallaxEffect= (ParallaxEffect) findViewById(R.id.parallaxEffect);
        //加载头
        View header = View.inflate(this, R.layout.header, null);
        //添加头  注意：要在设置适配器之前添加头
        //这个方法也可以写在parallaxEffect控件的第三个构造都犯法中
        parallaxEffect.addHeaderView(header);
        //找到header里面的ImageView，并设置给parallaxEffect
        ImageView header_image= (ImageView) header.findViewById(R.id.header_image);
        parallaxEffect.setHeaderImage(header_image);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Cheeses.NAMES);
        parallaxEffect.setAdapter(adapter);

    }
}
