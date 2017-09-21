package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyToggleButton extends View {

	private static final String nameSpace = "http://schemas.android.com/apk/res-auto";
	private Bitmap backgroundIcon;
	private Bitmap icon;
	private int iconleft;
	private int iconMaxLeft;

	int backgroundId ;
	int	iconId ;
	Boolean	openstate ;

	public MyToggleButton(Context context) {
		//super(context);
		this(context,null);
	}
	
	public MyToggleButton(Context context, AttributeSet attrs) {
		//super(context, attrs);
		this(context,attrs,0);
	}
	
	public MyToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//获取自定义属性的值，使用


		/**
		 * 获得我们所定义的自定义样式属性    安卓注释掉的还不能获取自定义的属性，绝对是坑
		 */
//		TypedArray a = context
//				.obtainStyledAttributes(attrs, R.styleable.ToggleButtonImageView);
//		a.get
//		backgroundId = a.getInteger(R.styleable.ToggleButtonImageView_backgrounicon,-1);
//		iconId  =a.getInteger(R.styleable.ToggleButtonImageView_icon,-1);
//		openstate  =a.getBoolean(R.styleable.ToggleButtonImageView_openstate,true);
//
//		a.recycle(); // 提示大家不要忘了回收资源

//
		int backgroundId = attrs.getAttributeResourceValue(nameSpace, "backgrounicon", -1);//根据属性的名称和命名空间，获取属性的值
		int iconId = attrs.getAttributeResourceValue(nameSpace, "icon", -1);
//		//设置图片给自定义控件
		if (backgroundId != -1 && iconId != -1) {
			setBackgroundAndIcon(backgroundId, iconId);
		}
		boolean openstate = attrs.getAttributeBooleanValue(nameSpace, "openstate", false);
		setState(openstate);

//		a.recycle();
	}
	
	
	//1.获取自定义控件显示的图片
	/**
	 * 接受传递过来的背景和按钮的图片
	 */
	public void setBackgroundAndIcon(int backgroundId,int iconId){
		//通过获取资源中的图片的id，获取图片，保存到bitmap中
		backgroundIcon = BitmapFactory.decodeResource(getResources(),backgroundId);
		icon = BitmapFactory.decodeResource(getResources(),iconId);
		//获取图片之间的最大距离
		iconMaxLeft = backgroundIcon.getWidth()-icon.getWidth();
	}
	//2.显示操作
	//控件绘制显示的流程，1.测量控件的宽高；2.排版（设置控件的位置）；3.绘制显示（根据控件的宽高和位置进行绘制显示）
	//第一步走完，才会执行第二步，第二步走完，才会执行第三步，第三步走完，才可以看到控件，否则是看不到控件，也不会获取到控件的宽高颜色等基本属性信息
	//2.1.测量控件的宽高
	/**
	 * 测量控件的宽高
	 * widthMeasureSpec ： 控件在布局文件中的宽
	 * heightMeasureSpec ：控件在布局文件中的高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//自己自定义实现绘制背景图片的宽高操作
		setMeasuredDimension(backgroundIcon.getWidth(), backgroundIcon.getHeight());//根据自己传递的宽高绘制控件的宽高
	}
	//2.2.排版
	/**
	 * 设置控件的位置
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	//2.3.绘制显示
	/**
	 * 绘制控件
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//1.绘制背景
		//bitmap : 根据哪个图片绘制新的图片
		//left : 绘制的图片显示在控件的x轴的哪个位置
		//top :绘制的图片显示在控件的y轴的哪个位置 
		//paint : 画笔
		//因为绘制的图片已经有形状颜色等信息了，所以就不需要使用画笔重新绘制了
		canvas.drawBitmap(backgroundIcon, 0, 0, null);
		
		//控制滑动的范围
		if (iconleft < 0) {
			iconleft=0;
		}else if(iconleft > iconMaxLeft){
			iconleft = iconMaxLeft;
		}
		//回调函数设置
		//1.抬起鼠标
		//2.将开关状态传递给回调函数方法
		boolean isopen = iconleft > 0;
		if (isHandUp) {
			//2.将开关状态传递给回调函数方法
			if (toggleListener != null) {
				toggleListener.toggle(isopen);
			}
			isHandUp = false;//为下一次按下抬起做准备
		}
		//2.绘制按钮
		canvas.drawBitmap(icon, iconleft, 0, null);
		
		super.onDraw(canvas);
	}
	//3.实现触摸事件进行滑动操作
	/**
	 * 控件的触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//按下
			iconleft = (int) (event.getX()-icon.getWidth()/2);
			break;
		case MotionEvent.ACTION_MOVE:
			//移动
			iconleft = (int) (event.getX()-icon.getWidth()/2);
			break;
		case MotionEvent.ACTION_UP:
			isHandUp = true;
			//抬起
			//判断是自动往左还是往右滑动
			if (event.getX() < backgroundIcon.getWidth()/2) {
				iconleft=0;
			}else{
				iconleft = iconMaxLeft;
			}
			
			break;
		}
		//onDraw(canvas):不能被调用
		invalidate();//调用invalidate方法，系统就会间接帮我们调用ondraw来进行绘制控件   (***)
		return true;//这里必须返回true,这样才能让以上事件执行下去  (***)
	}
	private onToggleListener toggleListener;
	private boolean isHandUp;
	//2.创建一个获取接口对象的方法
	public void setOnToggleListener(onToggleListener toggleListener){
		this.toggleListener = toggleListener;
	}
	//1.创建接口
	public interface onToggleListener{
		/**
		 * 获取开关状态的方法
		 * @param isOpen
		 */
		public void toggle(boolean isOpen);
	}
	/**
	 * 手动更改开关状态
	 * isOpen
	 * 		开关状态
	 */
	public void setState(boolean isOpen){
		isHandUp = true;
		if (isOpen) {
			//开启
			iconleft = iconMaxLeft;
		}else{
			//关闭
			iconleft = 0;
		}
		invalidate();//重新绘制的控件
	}














}
