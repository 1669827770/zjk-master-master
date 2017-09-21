package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoscrollViewPager extends ViewPager {

	/**
	 * 在布局文件中使用控件时，调用
	 * @param contexta
	 * @param attrs
	 */
	public NoscrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 重写onTouchEvent
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 干掉父类处理事件的功能，不调用父类的onTouchEvent
		return false;
	}

	
	// 覆盖ViewGroup拦截事件的方法
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 干掉ViewPager默认的拦截事件功能
		return false;//  自己不拦截孩子的事件
	}
}
