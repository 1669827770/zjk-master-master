package ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import view.FlowLayout;

/**
 * Created by admin on 2017-07-17.
 */

public class PictureActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);	// 创建ScrollView
        FlowLayout flowLayout = new FlowLayout(this);	// 创建FlowLayout
        flowLayout.setPadding(6, 6, 6, 6);
        for (int i = 0; i < list.size(); i++) {
            TextView textView =createRandomColorShapeSelectorTextView(this);
            textView.setText(list.get(i));
            flowLayout.addView(textView);

            if(i==list.size()-1){
                textView.setText(""+list.size());
            }
        }
        scrollView.addView(flowLayout);
        setContentView(scrollView);	// 把ScrollView作为根容器显示到界面上

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 获取当前进程的id
        int pid = android.os.Process.myPid();
        // 这个方法只能用于自杀操作
        android.os.Process.killProcess(pid);
    }

    /**
     * 创建一个带随机颜色图形的选择器的TextView
     * @return
     */
    public  TextView createRandomColorShapeSelectorTextView(Context context) {
        final TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(6, 6, 6, 6);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundDrawable(createRandomColorShapeSelector());
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = (String) textView.getText();

                if(text.equals("Glide的使用")){
                    startActivity(new Intent(getApplicationContext(), GlideActivity.class));
                }else if (text.equals("图片随手指缩放")){
                    startActivity(new Intent(getApplicationContext(), PictureZoomActivity.class));
                }else if (text.equals("缩放加载大图片")){
                    startActivity(new Intent(getApplicationContext(), ZoomLoadingLargePictureActivity.class));
                }else if (text.equals("撕衣服")){
                    startActivity(new Intent(getApplicationContext(), TearClothesActivity.class));
                }else if (text.equals("遍历SD卡上的所有图片")){
                    startActivity(new Intent(getApplicationContext(), ScanPicturesOnSDcardActivity.class));
                }else if (text.equals("拍照图库上传")){
                    startActivity(new Intent(getApplicationContext(), PhotoGalleryUploadActivity.class));
                }


            }
        });
        return textView;
    }

    public static ArrayList<String> list = new ArrayList<String>();
    static {
        list.add("Glide的使用");
        list.add("图片随手指缩放");
        list.add("缩放加载大图片");
        list.add("撕衣服");
        list.add("遍历SD卡上的所有图片 ");
        list.add("拍照图库上传");
        list.add("遍历SD卡上的所有图片 ");

    }

    /**
     * 创建一个带随机颜色图形的选择器的
     * @return
     */
    public static Drawable createRandomColorShapeSelector() {
        StateListDrawable stateListDrawable = new StateListDrawable();	// 创建一个选择器对象
        // 创建一个按下状态和按下状态对应的图片
        int[] pressState = {android.R.attr.state_pressed, android.R.attr.state_enabled};
        Drawable pressDrawable = createRandomColorShape();

        // 创建一个正常状态和正常状态对应的图片
        int[] normalState = {};
        Drawable normalDrawable = createRandomColorShape();

        stateListDrawable.addState(pressState, pressDrawable);	// 按下状态显示按下的Drawable
        stateListDrawable.addState(normalState, normalDrawable);// 正常状态显示正常的Drawable
        return stateListDrawable;
    }


    /**
     * 创建一个带随机颜色的图形
     * @return
     */
    public static Drawable createRandomColorShape() {
        GradientDrawable gradientDrawable = new GradientDrawable();	// 创建一个图形Drawable
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);		// 设置图形为矩形
        gradientDrawable.setCornerRadius(6);	// 设置矩形的圆角
        gradientDrawable.setColor(createRandomColor());	// 设置矩形的颜色
        return gradientDrawable;
    }


    /** 创建一个随机颜色 */
    public static int createRandomColor() {
        Random random = new Random();
        int red = 50 + random.nextInt(151);		// 50 ~ 200
        int green = 50 + random.nextInt(151);	// 50 ~ 200
        int blue = 50 + random.nextInt(151);	// 50 ~ 200
        int color = Color.rgb(red, green, blue);
        return color;
    }

}
