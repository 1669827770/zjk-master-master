package ui.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import videodemo.R;

/**
 * Created by admin on 2017-07-10.
 */

public class FullHeightChangeSevenLinesActivity  extends Activity implements View.OnClickListener {
    /** 指示简介的文字是否是显示的，true代表显示 */
    private boolean descIsOpen;
    ;
    private LinearLayout ll_desc;
    private ImageView iv_desc_arrow;
    private TextView tv_desc;
    /** 简介内容的原始高度 */
    private int originalHeight;

    private ScrollView sv_scroll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_fullheightchangesevenlines);
        initView();
        initListener();

    }

    private void initView() {
        // 第二块内容：官方、安全、无广告

        sv_scroll = (ScrollView) findViewById(R.id.sv_scroll);

        // 初始化简介的控件
        ll_desc = (LinearLayout) findViewById(R.id.ll_desc);
        iv_desc_arrow = (ImageView) findViewById(R.id.iv_desc_arrow);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

        // 通过这个方式可以监听View的布局发生改变
        tv_desc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                originalHeight = tv_desc.getHeight();
                if (originalHeight > 0) {
                    // 一定要记得移除这个监听器，只要onLayout方法重新被执行了，则这个onGlobalLayout方法就会被调用
                    tv_desc.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

    }

    private void initListener() {

        ll_desc.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_desc:
                descDisplayToggle();
                break;

        }
    }


    /** 应用简介显示的开关，，要么开，要么关 */
    private void descDisplayToggle() {
        int allLineHeight = getAllLineHeight();
        ValueAnimator valueAnimator;
        if (descIsOpen) {
            // 如果简介原来是打开的，则关闭：从完整的高变成7行的高
            valueAnimator = ValueAnimator.ofInt(allLineHeight, originalHeight);
            iv_desc_arrow.setImageResource(R.drawable.arrow_down);
        } else {
            // 如果简介原来是关闭的，则打开 ：从7行的高变成完整的高
            valueAnimator = ValueAnimator.ofInt(originalHeight, allLineHeight);
            iv_desc_arrow.setImageResource(R.drawable.arrow_up);
        }

        descIsOpen = !descIsOpen;

        // 监听ValueAnimator模拟出来的数值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (Integer) valueAnimator.getAnimatedValue();
                // 把高设置给简介内容的TextView
                tv_desc.getLayoutParams().height = height;
                tv_desc.requestLayout();	// 通知View刷新布局参数

                // 让TextView的高出来多少，则ScrllView就向上滚动多少
                sv_scroll.scrollBy(0, height);	// scrollBy方法是在原来的基础上再滚动的
            }
        });

        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    /** 获取简介所有文本全部显示的时候的高度 */
    private int getAllLineHeight() {
        TextView textView = new TextView(this);

        textView.setTextSize(14);	// 这个字体大小，一定要和布局里面的简介内容的TextView保持一致
        CharSequence text = tv_desc.getText();	// 取出简介里面的所有文本
        textView.setText(text);

        // 测量新的TextView，宽度的测试规格和原来的tv_desc保持一致
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(tv_desc.getWidth(), View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);

        return textView.getMeasuredHeight();
    }


}
