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

import ui.activity.eventbus.EventBusActivity;
import ui.activity.eventbus.eventBus2.tryeventbus2.FirstActivity;
import ui.activity.video.DownParallaxActivity;
import view.AddrToast;
import view.FlowLayout;

import static base.MyApplication.mContext;


/**
 * Created by admin on 2017-04-21.
 */

public class DaShenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                if(text.equals("自定义控件")){
                    startActivity(new Intent(getApplicationContext(), CustomViewActivity.class));
                }else if (text.equals("安卓动画")){
                    startActivity(new Intent(getApplicationContext(), AnimationActivity.class));
                }else if (text.equals("状态栏图标")){
                    startActivity(new Intent(getApplicationContext(), StatusbariconActivity.class));

                }else if (text.equals("Material design")){
                    startActivity(new Intent(getApplicationContext(), MaterialDesignActivity.class));

                }else if (text.equals("windowManager")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    AddrToast  at = new AddrToast(getApplicationContext());
                    at.show();
                    finish();
//                    Toast.makeText(getApplication(), "4444444444444444444", Toast.LENGTH_SHORT).show();
                }else if (text.equals("载入动画进度条")){
                    Intent intent = new Intent(mContext, StatusbariconActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("视频音频")){
                    startActivity(new Intent(DaShenActivity.this, VideoAudioActivity.class));

                }else if (text.equals("启动界面与主界面")){
                    startActivity(new Intent(DaShenActivity.this, StartAndMainInterfaceActivity.class));

                }else if (text.equals("图片问题，包括压缩，上传之类")){
                    startActivity(new Intent(DaShenActivity.this, PictureActivity.class));

                }else if (text.equals("进程间通讯")){
                    Intent intent = new Intent(mContext, IPCActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("2种观察者模式的使用")){
                    startActivity(new Intent(DaShenActivity.this,DownParallaxActivity.class));
                }else if (text.equals("6.0权限")){
                    Intent intent = new Intent(mContext, StatusbariconActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("7.0权限")){
                    Intent intent = new Intent(mContext, StatusbariconActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("EventBus")){
                    startActivity(new Intent(DaShenActivity.this,EventBusActivity.class));

                }else if (text.equals("EventBus2")){
                    startActivity(new Intent(DaShenActivity.this,FirstActivity.class));

                }else if (text.equals("MVP")){
                    Intent intent = new Intent(mContext, StatusbariconActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("安卓不常用的控件")){
                    Intent intent = new Intent(mContext, LessCommonlyUsedViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (text.equals("手机卫士")){
                    startActivity(new Intent(DaShenActivity.this, MobleSalfActivity.class));

                }else if (text.equals("dialog")){
                    startActivity(new Intent(DaShenActivity.this, DialogGroupActivity.class));

                }else if (text.equals("第三方SDK")){
                    startActivity(new Intent(DaShenActivity.this, ThirdPartySDKActivity.class));

                }else if (text.equals("设计模式")){
                    startActivity(new Intent(DaShenActivity.this, DesignPatternActivity.class));

                }


            }
        });
        return textView;
    }


    public static ArrayList<String> list = new ArrayList<String>();
    static {
        list.add("自定义控件");
        list.add("安卓动画");
        list.add("状态栏图标");
        list.add("Material design");
        list.add("windowManager");
        list.add("载入动画进度条");
        list.add("视频音频");
        list.add("启动界面与主界面");
        list.add("图片问题，包括压缩，上传之类");
        list.add("进程间通讯");
        list.add("2种观察者模式的使用");
        list.add("6.0权限");
        list.add("7.0权限");
        list.add("EventBus");
        list.add("EventBus2");
        list.add("MVP");
        list.add("安卓不常用的控件");
        list.add("手机卫士");
        list.add("dialog");
        list.add("第三方SDK");
        list.add("设计模式");
        list.add("设计模式");
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
