package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import adapter.CustomAdapter;

/**
 * Created by mtx  on 2017/4/17.
 */

public class CustomRecyclerView extends RecyclerView {
    //item的根布局
    private LinearLayout itemRoot;
    //上一次滑动的Item根布局
    private LinearLayout itemRootLast;
    //上次X轴的滑动坐标
    private int mlastX = 0;
    //上次Y轴的滑动坐标
    private int mlastY = 0;
    //滑动的最大距离
    private final int MAX_WIDTH = 100;
    private Context mContext;
    private Scroller mScroller;

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maxLength = dipToPx(mContext, MAX_WIDTH);
        int x = (int) event.getX();
        int y = (int) event.getY();
        final int  position;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //恢复上一次侧滑的ITEM
                if(itemRootLast !=null){
                    itemRootLast.scrollTo(0, 0);
                    invalidate();
                }
                //根据点击的坐标获取那个Item被点击了
                View view  = findChildViewUnder(x, y);
                if(view == null){
                    return false;
                }
                final CustomAdapter.CustomViewHolder viewHolder = (CustomAdapter.CustomViewHolder) getChildViewHolder(view);
                itemRootLast = itemRoot = (LinearLayout) viewHolder.textView.getParent();
                position= viewHolder.getAdapterPosition();

                if(mOnItemClickListener !=null){
                    viewHolder.deleteView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onClick(viewHolder.itemView,position);
                        }
                    });
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                if(itemRoot ==null){
                    return  false;
                }
                if(  Math.abs(mlastX -x)>0 && Math.abs(mlastX -x) > Math.abs(mlastY-y)){  //横向滑动
                    int scrollX = itemRoot.getScrollX();
                    int newScrollX = scrollX + mlastX - x;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > maxLength) {
                        newScrollX = maxLength;
                    }
                    itemRoot.scrollTo(newScrollX, 0);
                }


            }
            break;
            case MotionEvent.ACTION_UP: {
                if(itemRoot ==null){
                    return  false;
                }
                int scrollX = itemRoot.getScrollX();
                int newScrollX = scrollX + mlastX - x;
                if (scrollX > maxLength / 2) {
                    newScrollX = maxLength;
                } else {
                    newScrollX = 0;
                }
                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
            }
            break;
        }
        mlastX = x;
        mlastY = y;
        return super.onTouchEvent(event);
    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemRoot.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if(itemRootLast !=null){
                itemRootLast.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            }
        }
        invalidate();
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick(View view, int position);
    }

    public  void setOnItemClickListenre(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
