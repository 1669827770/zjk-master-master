package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import util.Cheeses;


/**
 * Created by Administrator on 2016/7/25.
 */
public class QuickIndexBar extends View {

    private Paint paint;
    private int cellWidth;
    private float cellHeight;
    private Rect rect;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //定义接口
    public interface OnLetterChangedListener{
        public void letterChanged(String letter);
    }
    private OnLetterChangedListener onLetterChangedListener;
    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        //画笔颜色
        paint.setColor(Color.GRAY);
        //加粗
        paint.setTypeface(Typeface.DEFAULT_BOLD);
//        paint.setFakeBoldText(true);
        paint.setTextSize(14);
        //空的矩形，存放文本的宽高
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < Cheeses.LETTERS.length; i++) {
            //计算文本的宽高
            paint.getTextBounds(Cheeses.LETTERS[i], 0, 1, rect);
            //文本的宽度
            int textWidth = rect.width();
            //文本的高度
            int textHeight = rect.height();
            float x = cellWidth * 0.5f - textWidth * 0.5f;
            float y = cellHeight * 0.5f + textHeight * 0.5f + cellHeight * i;
            //动态改变画笔颜色
            paint.setColor(currentIndex==i? Color.DKGRAY: Color.GRAY);
            canvas.drawText(Cheeses.LETTERS[i], x, y, paint);
        }
    }
    private int currentIndex=-1;
    private int preIndex=-1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //保存上一次索引位置
                preIndex=currentIndex;
                float y = event.getY();
                currentIndex= (int) (y/cellHeight);
                if (currentIndex>=0&&currentIndex<Cheeses.LETTERS.length){
//                    Utils.showToast(getContext(),Cheeses.LETTERS[currentIndex]);
                    if (onLetterChangedListener!=null&&preIndex!=currentIndex){
                        onLetterChangedListener.letterChanged(Cheeses.LETTERS[currentIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                currentIndex=-1;
                break;

            default:
                break;
        }
        invalidate();
        return true;
    }

    //测量完毕之后调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算单元格的宽高
        cellWidth = getMeasuredWidth();
        cellHeight = getMeasuredHeight() * 1.0f / Cheeses.LETTERS.length;
    }
}
