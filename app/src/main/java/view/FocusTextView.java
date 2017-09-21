package view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusTextView extends TextView {

	/**
	 * 这个方法不会被系统调用,只能被我们自己调用,可以调用这个方法给控件指定默认的样式
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 在布局文件中使用这个控件,当系统解析到这个控件时,系统会调用这个方法 创建 控件对象
	 * 
	 * @param context
	 * @param //attrs属性集合 里面存放时在布局文件中给控件指定的属性
	 */
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this(context, attrs, 0);
	}

	/**
	 * 在代码中new 这个对象时调用这个方法
	 * 
	 * @param context
	 */
	public FocusTextView(Context context) {
		// this(context, null);
		super(context);
	}
	
	/**
	 * 系统,就是通过调用这个方法判断TextView是否获得焦点了
	 * 如果返回true就表示已经获得焦点了
	 */
	@Override
	public boolean isFocused() {
		// 改为true表示欺骗系统TextView一直都在获得焦点
		return true;
	}
	
	/**
	 * 焦点发生变化后调用这个方法
	 * focused 当前是否已经获得焦点
	 */
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// 如果失去焦点了,就欺骗系统,又重新获得焦点了
		if(!focused){
			focused = isFocused();
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
		
	}

}
