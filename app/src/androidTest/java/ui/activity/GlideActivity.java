package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import util.GlideCircleTransform;
import util.GlideRoundTransform;
import videodemo.R;

/**
 * Created by admin on 2017-05-10.
 */

public class GlideActivity extends Activity {

    private ImageView mImgGlide;
    private ImageView mImgGlide2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);
        initView();
    }

    private void initView() {
        mImgGlide = (ImageView) findViewById(R.id.img_glide);
        Glide.with(this).load(R.drawable.guide_image2).bitmapTransform(new GlideCircleTransform(this)).into(mImgGlide);


        mImgGlide2 = (ImageView) findViewById(R.id.img_glide2);
        Glide.with(this).load(R.drawable.guide_image2).bitmapTransform(new GlideRoundTransform(this,50)).into(mImgGlide2);
    }
}
