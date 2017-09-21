package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import videodemo.R;

import static android.provider.ContactsContract.CommonDataKinds.Identity.NAMESPACE;


/**
 * 一定要继承相对布局,因为布局文件中的跟布局就是相对布局,在这个类中有可能会用回到相对布局独有的属性
 * 
 * @author fiercelf
 * 
 */
public class SettingItemView extends RelativeLayout {
//	xmlns:android="http://schemas.android.com/apk/res/android"
//	private final String NAMESPACE = "http://schemas.android.com/apk/res/com.itheima.mobilesafe88";

	int mintBackground ;
	boolean mIsShowToggle ;
	String mStringTitle ;
	private ImageView ivToggle;

	// 用来标记开关是否打开了,false表示关闭,true表示打开
	private boolean isToggleOn = false;

	/**
	 * 这个方法不会被系统调用,只能被我们自己调用,可以调用这个方法给控件指定默认的样式
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// 读取下面三个属性值:
		// itheima:sivBackground="first"
		// itheima:sivIsShowToggle="true"
		// itheima:sivTitle="设置自动更新
		// 得到boolean类型的属性值
		// 参1:命名空间
		// 参2:属性名称
		//方式1获取属性
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.SettingItemView);

		 mintBackground = a.getInteger(R.styleable.SettingItemView_sivBackground,0);
		 mIsShowToggle  =a.getBoolean(R.styleable.SettingItemView_sivIsShowToggle,true);
		mStringTitle  =a.getString(R.styleable.SettingItemView_sivTitle);

		a.recycle(); // 提示大家不要忘了回收资源

	/*	sivIsShowToggle = attrs.getAttributeBooleanValue(NAMESPACE, "sivIsShowToggle",
				true);
		sivBackground = attrs.getAttributeIntValue(NAMESPACE, "sivBackground", 0);
		sivTilte = attrs.getAttributeValue(NAMESPACE, "sivTitle");*/

		// 初始化控件
		initView();
	}

	/**
	 * 在布局文件中使用这个控件,当系统解析到这个控件时,系统会调用这个方法 创建 控件对象
	 * 
	 * @param context
	 * @param attrs属性集合
	 *            里面存放时在布局文件中给控件指定的属性
	 */
	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 在代码中new对象时调用这个方法
	 * 
	 * @param context
	 */
	public SettingItemView(Context context) {
		this(context, null);
	}

	private void initView() {
		// 1.把布局文件加载为view界面
		View view = View.inflate(getContext(), R.layout.item_setting_view, this);
		/*
		 * // 3.把界面显示在自己身上 addView(view);
		 */
		// 2.得到布局文件中的控件对象
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);

		ivToggle = (ImageView) view.findViewById(R.id.iv_toggle);

		// 使用给自定义属性赋的值sivIsShowToggle,来控制开关是否显示
		if (mIsShowToggle) {
			// INVISIBLE :不可见 ,GONE:不可见,VISIBLE 可见
			ivToggle.setVisibility(View.VISIBLE);
		} else {
			ivToggle.setVisibility(View.GONE);
		}

		// 显示开关的状态
		if (isToggleOn) {
			// 开关打开
			ivToggle.setImageResource(R.drawable.on);
		} else {
			// 开关关闭
			ivToggle.setImageResource(R.drawable.off);
		}

		// 显示给自定义属性赋的值sivTilte
		tvTitle.setText(mStringTitle);

		// 使用自定义属性赋的值sivBackground,来控制控件显示的背景
		switch (mintBackground) {
		case 0: // first
			setBackgroundResource(R.drawable.selector_siv_first);
			break;

		case 1: // midlle
			setBackgroundResource(R.drawable.selector_siv_middle);
			break;

		case 2: // last
			setBackgroundResource(R.drawable.selector_siv_last);
			break;

		}

	}

	/**
	 * 设置开关的方法
	 * 
	 * @param isToggle
	 *            true表示开关打开,false表示关闭
	 */
	public void setToggleOn(boolean isToggleOn) {
		// 记住给开关设置的状态
		this.isToggleOn = isToggleOn;

		if (isToggleOn) {
			// 开关打开
			ivToggle.setImageResource(R.drawable.on);
		} else {
			// 开关关闭
			ivToggle.setImageResource(R.drawable.off);
		}
	}

	/**
	 * 开关切换
	 */
	public void toggle() {
		setToggleOn(!isToggleOn);
	}

	/**
	 * 判断开关是否打开了,false表示关闭,true表示打开
	 * 
	 * @return
	 */
	public boolean isToggle() {
		return isToggleOn;
	}

}
