package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017-07-07.
 */

public class CencleCircle extends View {


//画笔
   private Paint  mCirclePaint  = new Paint();
    private int  mCircleRadius=100;  //圆的半径
    public CencleCircle(Context context) {
        super(context,null);
    }

    public CencleCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CencleCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        //设置抗锯齿
        mCirclePaint.setAntiAlias(true);
        //设置防抖动
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float x = getWidth()/2;//右移一位相当于除以2，即宽度的一半  布局中为match_parent所以是中间
        float y = getHeight()/2 ;

        canvas.drawCircle(x,y,mCircleRadius,mCirclePaint);
    }
}
