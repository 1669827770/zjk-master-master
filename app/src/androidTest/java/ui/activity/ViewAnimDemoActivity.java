package ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import animtypeevaluator.BounceEaseOut;
import videodemo.R;

/**
 * Created by admin on 2017-07-13.
 */
public class ViewAnimDemoActivity extends AppCompatActivity {

    /**
     * DropOut
     */
    private TextView mTvDropout;
    /**
     * DropIn
     */
    private TextView mTvDropin;
    /**
     * hello word
     */
    private TextView mTvView;

    public static void startAction(Context context) {
        Intent mIntent = new Intent(context, ViewAnimDemoActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim_demo);
        initView();
        initlister();

    }

    private void initlister() {
        mTvDropout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvView.setVisibility(View.VISIBLE);
                setDropout();
            }
        });

        mTvDropin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDropin();
            }
        });

    }


    private void setDropout() {
        int distance = mTvView.getTop() + mTvView.getHeight();
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mTvView, "alpha", 0, 1);
        BounceEaseOut b = new BounceEaseOut(1000);
        ObjectAnimator dropout = ObjectAnimator.ofFloat(mTvView, "translationY", -distance, 0);
        dropout.setEvaluator(b);

        animSet.playTogether(alpha, dropout);
        animSet.setDuration(1200);
        animSet.start();
    }


    private void setDropin() {
        int distance = mTvView.getTop() + mTvView.getHeight();
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mTvView, "alpha", 0, 1);
        BounceEaseOut b = new BounceEaseOut(1000);
        ObjectAnimator dropout = ObjectAnimator.ofFloat(mTvView, "translationY", 0, distance*2);
        dropout.setEvaluator(b);
        animSet.playTogether(alpha, dropout);
        animSet.setDuration(1200);
        animSet.start();
    }

    private void initView() {
        mTvDropout = (TextView) findViewById(R.id.tv_dropout);
        mTvDropin = (TextView) findViewById(R.id.tv_dropin);
        mTvView = (TextView) findViewById(R.id.tv_view);
    }
}
