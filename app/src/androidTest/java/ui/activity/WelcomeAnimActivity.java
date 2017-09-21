package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import videodemo.R;

/**
 * Created by admin on 2017-06-11.
 */

public class WelcomeAnimActivity  extends Activity {

    private RelativeLayout rl_welcome_bg;

    public static final String IS_APP_FIRST_OPEN = "is_app_first_open";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeanim);
        initView();
        animate();
    }

    private void animate() {
        // 旋转动画，基于中心点，顺时针转一圈
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);// 让动画保持在结束时的位置

        // 缩放动画，从无到展示1倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        // 渐变动画，从无到有
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        // 把三个动画同时执行
        AnimationSet animationSet = new AnimationSet(false);// 是否共享一个差值器
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);

        rl_welcome_bg.startAnimation(animationSet);

        // 监听动画，当动画完成后切换界面
        animationSet.setAnimationListener(new MyAnimationListener());
    }
    /**
     * 监听动画完成的跳转逻辑
     * @author h
     *
     */
    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 当动画完成后，跳转界面
            // 取是否打开过应用的标记
//            boolean isAppFirstOpen = CacheUtil.getBoolean(getApplicationContext(), IS_APP_FIRST_OPEN, true);
//            if(isAppFirstOpen){
//                // 是第一次打开，跳到引导界面
//                System.out.println("是第一次打开，跳到引导界面");
//                startActivity(new Intent(WelcomeUI.this,GuideUI.class));
//            }else{
//                // 已经打开过，跳到主界面
//                System.out.println("已经打开过，跳到主界面");
//                startActivity(new Intent(WelcomeUI.this,MainUI.class));
//            }
//            // 关掉自己
//            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }


    private void initView() {
        rl_welcome_bg = (RelativeLayout) findViewById(R.id.rl_welcome_bg);
    }

}
