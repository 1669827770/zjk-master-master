package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class DrawView extends View {

    public float currentX = 80;
    public float currentY = 60;


    public DrawView(Context context)
    {
        super(context);
    }

    public DrawView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 10, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.currentX = event.getX(); //触摸座标X
        this.currentY = event.getY(); //触摸座标Y
        invalidate();//重绘组件
        return true;//返回true：事件已处理，此处必须返回true，否则小球移动不了。
    }
}