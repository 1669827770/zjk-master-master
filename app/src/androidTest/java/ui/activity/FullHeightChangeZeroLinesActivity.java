package ui.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import videodemo.R;

/**
 * Created by admin on 2017-07-10.
 */

public class FullHeightChangeZeroLinesActivity extends Activity implements View.OnClickListener {

    private LinearLayout ll_save_icon;
    private LinearLayout ll_save_desc;
    private ImageView iv_arrow;
    private LinearLayout ll_save_root;

    /** 指示安全模块的文字是否是显示的，true代表显示 */
    private boolean saveDescIsOpen;
    /** 指示简介的文字是否是显示的，true代表显示 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullheightchangezerolines);
        initView();
        initListener();
    }

    private void initView() {

        // 第二块内容：官方、安全、无广告
        ll_save_root = (LinearLayout) findViewById(R.id.ll_save_root);
        ll_save_icon = (LinearLayout) findViewById(R.id.ll_save_icon);
        ll_save_desc = (LinearLayout) findViewById(R.id.ll_save_desc);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
//		for (int i = 0; i < ll_save_desc.getChildCount(); i++) {
//			ll_save_icon.getChildAt(i).setVisibility(View.GONE);	// 先把容器中的图标隐藏
//			ll_save_desc.getChildAt(i).setVisibility(View.GONE);	// 先把容器中的TextView隐藏
//		}
        // 最开始先隐藏安全图标文字描述
        ll_save_desc.getLayoutParams().height = 0;
        ll_save_desc.requestLayout();	// 通知重新刷新View


    }

    private void initListener() {
        ll_save_root.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_save_root:	// 点击了安全模块的根容器
                saveDisplayToggle();
                break;
        }
    }


    /**  安全模块的文字描述的显示开关，要么开，要么关 */
    private void saveDisplayToggle() {
        ll_save_desc.measure(0, 0);	// 给容器传入未指定的测量规格，让容器自己测量一下自己
        int measuredHeight = ll_save_desc.getMeasuredHeight();

        ValueAnimator animator;	// 这个类用于模拟数值的变量，比如说模拟从10 ~ 0
        if (saveDescIsOpen) {
            // 隐藏：从完整的高慢慢变成0
            animator = ValueAnimator.ofInt(measuredHeight, 0);	// 创建一个模拟从measuredHeight到0的动画
            iv_arrow.setImageResource(R.drawable.arrow_down);	// 显示一个向下的箭头
        } else {
            // 显示：从0慢慢变成完整的高
            animator = ValueAnimator.ofInt(0, measuredHeight);	// 创建一个模拟从0到measuredHeight的动画
            iv_arrow.setImageResource(R.drawable.arrow_up);		// 显示一个向下的箭头
        }

        saveDescIsOpen = !saveDescIsOpen;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 这个方法用于不停地接收模拟出来的数值
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (Integer) valueAnimator.getAnimatedValue();	// 拿到模拟出来的值
                // 这个高会不停地变
                ll_save_desc.getLayoutParams().height = height;
                ll_save_desc.requestLayout();	// 通知重新刷新View
            }
        });

        animator.setDuration(200);	// 设置模拟数值所需要的时间
        animator.start();			// 开始模拟数值
    }
}
