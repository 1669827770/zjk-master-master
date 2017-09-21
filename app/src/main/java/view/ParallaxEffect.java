package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxEffect extends ListView {
    private int originalHeight;
    private int height;
    private ValueAnimator valueAnimator;

    public ParallaxEffect(Context context) {
        this(context, null);
    }

    public ParallaxEffect(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }
    //接收头里面的图片
    private ImageView header_image;
    public void setHeaderImage(ImageView header_image) {
        this.header_image = header_image;
        //获取图片的额本质高度 ，即右键--》属性--》详细信息  查看到的图片信息 (这个是为了限制newHeight的高度)
        this.originalHeight = header_image.getDrawable().getIntrinsicHeight();
        //获取ImageView的高度  这两个方法是获取不到图片的高度的，因为这个setHeaderImage是在mainActivity的oncreat方法是执行的，虽然没有加载完，但是我们已经布局完了，那么我们就可以拿到他的布局参数
//        int height = header_image.getHeight();
//        int height = header_image.getMeasuredHeight();
        height = header_image.getLayoutParams().height; //获取这个高度是为了后面下拉回弹的动画效果
        Log.i("test","height="+ height);
    }

    /**
     * 当listview超出滑动范围的时候被调用
     *
     * @param deltaX
     * @param deltaY   垂直方向的偏移量  顶部下拉为-  底部上拉为正数
     * @param scrollX
     * @param scrollY  垂直方向滑动的距离
     * @param scrollRangeX
     * @param scrollRangeY      滑动的范围
     * @param maxOverScrollX
     * @param maxOverScrollY        最大超出的滑动距离
     * @param isTouchEvent    true表示是触摸事件    false表示惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//Log.i("test", " deltaY = [" + deltaY + "],  scrollY = [" + scrollY + "],  " +
//"scrollRangeY = [" + scrollRangeY + "], maxOverScrollY = [" + maxOverScrollY + "], isTouchEvent = [" + isTouchEvent + "]");
        //改变图片的高度
        if (isTouchEvent&&deltaY<0){   //首先得是触摸事件
            //计算图片新的高度     乘以0.6下拉就会有一种阻力效果
            int newHeight = (int) (header_image.getHeight() + Math.abs(deltaY)*0.6f);
            //限制newHeight的高度
            if (newHeight>originalHeight*0.5f){
                newHeight= (int) (originalHeight*0.5f);
            }
            header_image.getLayoutParams().height=newHeight; //将新高度设置了图片
            header_image.requestLayout();  //更改完刷新
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
//所以需要上面限制图片的新高度
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (valueAnimator!=null&&valueAnimator.isRunning()){
                    valueAnimator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //ofFloat的参数是可变参数，意义就是从一个值到另一个值进行变化（怎么一个变化过程），第一个参数下拉是当前的高度，第二个参数是控件开始的高度
                valueAnimator = ValueAnimator.ofFloat(header_image.getHeight(), height);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //获取header_image的新高度     //float不能强转成int,
                        float newHeight = (float) animation.getAnimatedValue();//一个拿具体一个拿百分比
                        header_image.getLayoutParams().height = (int) newHeight;
                        header_image.requestLayout();
                    }
                });
                valueAnimator.setInterpolator(new OvershootInterpolator(5));
                valueAnimator.setDuration(300);
                valueAnimator.start();
                break;
            default:break;
        }
        //注意：由ListView来执行滑动操作，不能返回true。
        return super.onTouchEvent(ev);
    }
}