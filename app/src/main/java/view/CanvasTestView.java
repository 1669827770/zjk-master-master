package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by sszz on 2016/12/9.
 */

public class CanvasTestView extends View{

	private Paint paint;

	//主要用于创建自定义控件实例(就是new一个控件)
	public CanvasTestView(Context context) {
		this(context,null);
	}
	//主要用于用户将当前自定义控件声明在布局文件中
	//声明布局文件中注意:必须声明控件的"包名+类名"
	public CanvasTestView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	//主要用于用户将当前自定义控件声明在布局文件中,并且加入样式
	public CanvasTestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	//构造方法的意义:用于初始化加载数据,即自定义控件流程的第一个流程
	private void init() {
		Log.i("test","初始化");
		paint = new Paint();
		//设置画笔的颜色
		paint.setColor(Color.RED);
		//绘制空心图形
		paint.setStyle(Paint.Style.STROKE);
		//去除锯齿,毛刺
		//开启这个功能,将会耗费系统资源
		paint.setAntiAlias(true);
	}
	//用于封装x,y坐标的坐标点
	private PointF facePoint=new PointF(240,400);
	private int faceRadius=200;

	private PointF line1Start=new PointF(140,250);
	private PointF line1End=new PointF(340,250);
	private PointF line2Start=new PointF(240,250);
	private PointF line2End=new PointF(240,500);
	private PointF line3End=new PointF(150,450);

	private PointF eye1Point=new PointF(170,330);
	private PointF eye2Point=new PointF(310,330);
	private int eyeRadius=60;
	//RectF:矩形
	//参数1,2,3,4:矩形的左上右下
	//定义一个矩形
//参数1:矩形的左边相对于屏幕左边缘的距离
//参数2:矩形的上边相对于屏幕上边缘的距离
//参数3:矩形的右边相对于屏幕左边缘的距离
//参数4:矩形的下边相对于屏幕上边缘的距离
	private RectF mouth=new RectF(60,300,420,550);

	private Rect leftEar=new Rect(30,300,80,500);

	private float startAngle=380f;
	private float sweepAngle=140f;

	private float[] pts={10,20,30,40,50,60};
	//在绘制阶段调用这个方法
	//参数:画布
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		//canvas的平移或旋转实际是对坐标系的平移或旋转
		//平移
//		canvas.translate(200,0);
		//旋转
//		canvas.rotate(-45);   旋转-45就是逆时针旋转45
		//缩放
		canvas.scale(0.2f,0.2f);

		//参数1,2:圆心的x,y坐标
		//参数3:radius,圆的半径
		//参数4:画笔
		canvas.drawCircle(facePoint.x,facePoint.y,faceRadius,paint);
		//参数1,2:线段的起始点
		//参数3,4:线段的终点
		//参数5:画笔
		canvas.drawLine(line1Start.x,line1Start.y,line1End.x,line1End.y,paint);
		canvas.drawLine(line2Start.x,line2Start.y,line2End.x,line2End.y,paint);
		canvas.drawLine(line2End.x,line2End.y,line3End.x,line3End.y,paint);

		canvas.drawCircle(eye1Point.x,eye1Point.y,eyeRadius,paint);
		canvas.drawCircle(eye2Point.x,eye2Point.y,eyeRadius,paint);
		//参数1:rectf:矩形,是圆弧所在圆的外接矩形
		//参数2:弧的起始角度
		//参数3:弧的弧度
		//参数4:如果为true,则为扇形,否则为弧形
		//参数5:画笔
		canvas.drawArc(mouth,startAngle,sweepAngle,false,paint);


		//参数1:绘制的矩形
		//参数2:画笔
		canvas.drawRect(leftEar,paint);
		//参数1:绘制的点击的坐标的集合{x0,y0,x1,y1,x2,y2...}
		//参数2:从参数1对应集合中的哪个点作为起始点,作为起始点的点为横坐标,后面依次顺延
		//参数3:需要使用集合中的几个元素
		//参数4:画笔
		canvas.drawPoints(pts,0,pts.length,paint);   //绘制多个点

		canvas.restore();
	}
}
