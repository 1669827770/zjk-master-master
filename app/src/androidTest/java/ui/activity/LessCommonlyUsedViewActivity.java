package ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import util.SpeechUtils;
import view.FlowLayout;

import static base.MyApplication.mContext;

/**
 * Created by admin on 2017-07-19.
 */

public class LessCommonlyUsedViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);	// 创建ScrollView
        FlowLayout flowLayout = new FlowLayout(this);	// 创建FlowLayout
        flowLayout.setPadding(6, 6, 6, 6);
        for (String str : list) {
            TextView textView =createRandomColorShapeSelectorTextView(this);
            textView.setText(str);
            flowLayout.addView(textView);
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
     *
     * TTS是Text To Speech的缩写，即“从文本到语音”，是人机对话的一部分，让机器能够说话。
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

                if(text.equals("DatePickerDialog")){
                    startActivity(new Intent(getApplicationContext(), DatePickerDialogActivity.class));
                }else if (text.equals("安卓动画")){
                    startActivity(new Intent(getApplicationContext(), AnimationActivity.class));
                }else if (text.equals("安卓动画")){
                    startActivity(new Intent(getApplicationContext(), AnimationActivity.class));
                }else if (text.equals("TextToSpeech")){

//                    SpeechUtils.getInstance(mContext).speakText("All along, Li Bingbing as the Ice Queen appear in front of the audience, as long as appeared to attract audience attention, but this time Li Bingbing also very helpless, because she met a female translator, even foreigners are not calm");
                    SpeechUtils.getInstance(mContext).speakText("好好学习，天天向上");  //只能读英文

                }else if (text.equals("计时器")){
                    startActivity(new Intent(getApplicationContext(), TimerActivity.class));
                }


            }
        });
        return textView;
    }


    public static ArrayList<String> list = new ArrayList<String>();
    static {
        list.add("DatePickerDialog");
        list.add("安卓动画");
        list.add("安卓动画");
        list.add("TextToSpeech");
        list.add("计时器");

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
