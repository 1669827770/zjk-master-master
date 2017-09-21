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

import dialog.CenterDialog;
import dialog.FromBottomToTopDialog;
import dialog.FromLeftToRightDialog;
import dialog.FromRightToLeftDialog;
import dialog.FromTopToBottomDialog;
import dialog.NoCancleDialog;
import videodemo.R;
import view.FlowLayout;

/**
 * Created by admin on 2017-07-10.
 */

public class DialogGroupActivity  extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    /**
     * 创建一个带随机颜色图形的选择器的TextView
     * @return
     */
    public TextView createRandomColorShapeSelectorTextView(Context context) {
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
                }else if (text.equals("圆角矩形从下到上的dialog")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    FromBottomToTopDialog fromBfottomToTopDialog = new FromBottomToTopDialog(DialogGroupActivity.this);
                    fromBfottomToTopDialog.show();
                }else if (text.equals("圆角矩形从上到下的dialog")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    FromTopToBottomDialog fromTofpToBottomDialog = new FromTopToBottomDialog(DialogGroupActivity.this);
                    fromTofpToBottomDialog.show();
                }else if (text.equals("圆角矩形从左到右的dialog")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    FromLeftToRightDialog fromLeftToRightDialog = new FromLeftToRightDialog(DialogGroupActivity.this);
                    fromLeftToRightDialog.show();

                }else if (text.equals("圆角矩形从右到左的dialog")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    FromRightToLeftDialog fromRightToLeftDialog = new FromRightToLeftDialog(DialogGroupActivity.this);
                    fromRightToLeftDialog.show();

                }else if (text.equals("中间的圆角矩形的dialog")){
                    //这里要是传getApplication,getApplicationContext都不能显示dialog
                    CenterDialog addrDialog2 = new CenterDialog(DialogGroupActivity.this, R.style.filletDialog);
                    addrDialog2.show();

                }else if (text.equals("无法取消的dialog")){
                   //这里要是传getApplication,getApplicationContext都不能显示dialog
                    NoCancleDialog nocancleDialog = new NoCancleDialog(DialogGroupActivity.this,R.style.filletDialog);
                    nocancleDialog.setCancelable(false);
                    nocancleDialog.show();
                }

            }
        });
        return textView;
    }

    public static ArrayList<String> list = new ArrayList<String>();
    static {
        list.add("自定义控件");
        list.add("圆角矩形从下到上的dialog");
        list.add("圆角矩形从上到下的dialog");
        list.add("圆角矩形从左到右的dialog");
        list.add("圆角矩形从右到左的dialog");
        list.add("中间的圆角矩形的dialog");
        list.add("无法取消的dialog");

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
