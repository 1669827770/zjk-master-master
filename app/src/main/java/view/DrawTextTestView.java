package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sszz on 2016/12/9.
 */

public class DrawTextTestView extends View {

	private Paint paint;

	public DrawTextTestView(Context context) {
		this(context,null);
	}

	public DrawTextTestView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public DrawTextTestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.BLUE);
		//文本字体大小
		paint.setTextSize(30);
		//粗体
		paint.setFakeBoldText(true);
		paint.setUnderlineText(true);
		//文本中间有横线穿过
		paint.setStrikeThruText(true);
		//canvas绘制文本填入的x和y(240,400)为文本基准点
		//如设置Paint.Align.CENTER:基准点位于文本中间
		//Paint.Align.RIGHT:基准点位于文本右边（我觉得应该是文本位于基准点的右边）
		//Paint.Align.LEFT:基准点位于文本左边（同上）
		//文本居中
//		paint.setTextAlign(Paint.Align.RIGHT);
		paint.setTextAlign(Paint.Align.CENTER);

		//对于文本的x方向缩放
		//参数为方法的倍数
		paint.setTextScaleX(2);
	}
	private String text="我是中国人";
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//参数1:文本内容
		//参数2,3:绘制文本的x,y坐标
		canvas.drawText(text,240,400,paint);
	}
}
