package view.ruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.text.DecimalFormat;

import videodemo.R;


// just test
public class RuleView extends View {

	Paint paint;

	private Context context;

	public int maxValue = 500;  //  修改尺子最大刻度
	/**
	 * 起点x的坐标
	 */
	private float startX ;

	private float startY ;
	/**
	 * 刻度线的长度
	 */
	private float yLenght ;
	/**
	 * 刻度的间隙
	 */
	private float gap;
	/**
	 * 文本的间隙
	 */
	private float textGap = 10f;
	/**
	 * 短竖线的高度
	 */
	private float smallHeight = 10f;
	/**
	 * 长竖线的高度
	 */
	private float largeHeight = 22f;
	/**
	 * 中竖线的高度
	 */
	private float middleHeight = 16f;
	
	/**
	 * 文本显示格式化
	 */
	private DecimalFormat format;

	private DisplayMetrics metrics = null;
	/**
	 * 文本的字体大小
	 */
	private float mFontSize;

	private Handler mScrollHandler = null;

	private MyHorizontalScrollView horizontalScrollView;

	private int mCurrentX = -999999999;
	/**
	 * 刻度进制
	 */
	private float unit = 10f;

	boolean isDraw = true;

	public RuleView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public void setMaxValues(int maxValue){
		this.maxValue=maxValue;
	}

	public void setHorizontalScrollView(MyHorizontalScrollView horizontalScrollView) {
		this.horizontalScrollView = horizontalScrollView;

		this.horizontalScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				final int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:

					break;
				case MotionEvent.ACTION_MOVE:

					mScrollHandler.removeCallbacks(mScrollRunnable);
					break;
				case MotionEvent.ACTION_UP:

					mScrollHandler.post(mScrollRunnable);
					break;
				}
				return false;
			}
		});
	}

	public RuleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init();
	}

	public void init() {

//		format = new DecimalFormat("0.0");
		format = new DecimalFormat("0");  //去掉就没有0了，原来是上面的那个

		metrics = new DisplayMetrics();
		WindowManager wmg = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wmg.getDefaultDisplay().getMetrics(metrics);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL);
		//控制线条粗细
		paint.setStrokeWidth(getResources().getDimension(R.dimen.dp1));
		paint.setColor(Color.parseColor("#ffffffff"));

		mFontSize = Util.dip2px(context, 8);
		startY = Util.dip2px(context, 20f);
		yLenght = Util.dip2px(context, 10);
//		gap = Util.dip2px(context, 8f);

//		String token = PreferenceUtils.getString(mContext, Constant.PREFERENCE_KEY_TOKEN, "");
//		User user = ApiUserDb.getUser(mContext, token);
//		int role = user.role;
//		if(role==0){
			gap= (float) ((Util.getScreenWidth(getContext()) * 1.0D-Util.dip2px(getContext(),6))/200);  //修改尺子一个界面显示多少个
//		}else {
//			gap=Util.getScreenWidth(mContext)/3000;  //修改尺子一个界面显示多少个
//		}

		//这个是修改尺子起点的地方0
//		startX = Util.getScreenWidth(context)/ 2.0f- getResources().getDimension(R.dimen.activity_horizontal_margin)  ;
		startX =  Util.dip2px(context,0) ;

		// + getResources().getDimension(R.dimen.text_h2)/2.0f
		// Util.dip2px(context, 13f) +

		mScrollHandler = new Handler(context.getMainLooper());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//		float width =  maxValue * gap + Util.getScreenWidth(context) - getResources().getDimension(R.dimen.dp12)*2.0f ;
		float width =  maxValue * gap  ;

		// int widthMode = MeasureSpec.getMode(heightMeasureSpec);
		// if(widthMode == MeasureSpec.AT_MOST){
		// Log.d("TAG", "mode::AT_MOST");
		// }else if(widthMode == MeasureSpec.EXACTLY){
		// Log.d("TAG", "mode::EXACTLY");
		// }else if(widthMode == MeasureSpec.UNSPECIFIED){
		// Log.d("TAG", "mode::UNSPECIFIED");
		// }
		setMeasuredDimension((int) width, heightMeasureSpec);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		//画刻度线
		paint.setColor(Color.parseColor("#ffcccccc"));// 刻度颜色
		for (int i = 0; i <= maxValue; i++) {
              String s=i+"";
			if (i % 5 == 0) {
				if(s.contains("5")){
					yLenght = Util.dip2px(context, middleHeight);
				}else{
					yLenght = Util.dip2px(context, largeHeight);
				}

			} else {
				yLenght = Util.dip2px(context, smallHeight);
			}
			canvas.drawLine(i * gap + startX, startY, i * gap + startX, yLenght
					+ startY, paint);
		}

		paint.setTextSize(mFontSize);

		//每10个刻度写一个数字
		textGap = gap * unit;

		// 画刻度文字30
		paint.setColor(Color.parseColor("#ff666666"));// 文字颜色
        float width;
		for (int i = 0; i <= maxValue / unit; i++) {
            if(i%2==0){  //如果是偶数才画
                //从0开始画
                String text = format.format(i + 0) + "";
                if(i==0){
                    // 获取文本的宽度
                    width = Util.px2dip(context, calculateTextWidth(text)-40) / 2f;
				} else if (i==20){
					width = Util.px2dip(context, calculateTextWidth(text)+70) / 2f;
                } else{
                    width = Util.px2dip(context, calculateTextWidth(text)) / 2f;
                }


                canvas.drawText(
                        text,
                        startX - width + i * textGap,
                        (startY + Util.dip2px(context, 8))
                                + Util.dip2px(context, 28), paint);
            }

		}
	}

	/**
	 * 获取TextView中文本的宽度
	 * 
	 * @param text
	 * @return
	 */
	private float calculateTextWidth(String text) {
		if (TextUtils.isEmpty(text)) {
			return 0;
		}
		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(mFontSize * metrics.scaledDensity);
		final float textWidth = textPaint.measureText(text);

		return textWidth;
	}

	DecimalFormat df = new DecimalFormat("0.0");

//	private OnScrollStateChangedListener mOnScrollStateChangedListener = null;
//
//	public void setOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
//		mOnScrollStateChangedListener = listener;
//	}
//
//	public void setCurrentScrollState(VideoThumbHorizontalListView.OnScrollStateChangedListener.ScrollState newScrollState) {
//		Log.i("Jason", "setCurrentScrollState  newScrollState  = " + newScrollState);
//		Log.i("Jason", "setCurrentScrollState  mCurrentScrollState  = " + mCurrentScrollState);
//		if (mOnScrollStateChangedListener != null) {
//			mOnScrollStateChangedListener.onScrollStateChanged(newScrollState,_scrolledOffset);
//		}
//		mCurrentScrollState = newScrollState;
//	}
//
//	public interface OnScrollStateChangedListener {
//		enum ScrollState {
//			/**
//			 * The view is not scrolling. Note navigating the list using trackball counts as being in the
//			 * idle state since these transitions are not animated.
//			 */
//			SCROLL_STATE_IDLE,
//
//			/**
//			 * The user is scrolling using touch, and their fingers are still on the screen.
//			 */
//			SCROLL_STATE_TOUCH_SCROLL,
//
//			/**
//			 * The user had previously been scrolling using touch and had performed a fling. The animation
//			 * is now coasting to stop.
//			 */
//			SCROLL_STATE_FLING
//		}
//
//		/**
//		 * Callback method to be invoked when the scroll state changes.
//		 * @param scrollState The current scroll state
//		 */
//		void onScrollStateChanged(VideoThumbHorizontalListView.OnScrollStateChangedListener.ScrollState scrollState, int scrollDirection);
//	}
//

	/**
	 * 当滑动尺子的时候
	 * @param l
	 * @param t
	 * @param oldl
	 * @param oldt
	 */

	int scrollWidth = 0;

	public void setScrollerChanaged(int l) {
		// 滑动的距离
		scrollWidth = l;
		float number = scrollWidth / gap;
		float result = number / unit;

		listener.onSlide(result);
	}

	public onChangedListener listener;

	public interface onChangedListener {

		void onSlide(float number);
	}

	public void onChangedListener(onChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * 滚动监听线程
	 */
	private Runnable mScrollRunnable = new Runnable() {

		@Override
		public void run() {
			if (mCurrentX == horizontalScrollView.getScrollX()) {// 滚动停止了

				try {

					float x = horizontalScrollView.getScrollX();
					float value = (x / (gap * unit));// 当前的值
					String s = df.format(value);

					// 滑动到11.0 ok
					int scrollX = (int) (Double.parseDouble(s) * gap * unit);

					horizontalScrollView.smoothScrollTo(scrollX, 0);

				} catch (NumberFormatException numExp) {
					numExp.printStackTrace();
				}
				mScrollHandler.removeCallbacks(this);
			} else {
				mCurrentX = horizontalScrollView.getScrollX();
				mScrollHandler.postDelayed(this, 50);
			}
		}
	};

	/**
	 * 设置默认刻度尺的刻度值,不会滚动到相应的位置
	 * 
	 * @param scaleValue
	 */
	public void setDefaultScaleValue(float scaleValue) {

		final int scrollX = (int) ((scaleValue - 1.0f) * gap * unit);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				horizontalScrollView.smoothScrollTo(scrollX, 0);
			}
		}, 100);
	}

	/**
	 * 设置刻度最小值
	 * 
	 * @return
	 */
	public void setMinScaleValue(Float minScaleValue) {
		// this.minScaleValue = minScaleValue;
	}

	/**
	 * 获取刻度最大值
	 * 
	 * @return
	 */
	public Float getMaxScaleValue() {
		// return maxScaleValue;
		return 33.0f;
	}

	/**
	 * 设置刻度最大值
	 * 
	 * @return
	 */
	public void setMaxScaleValue(Float maxScaleValue) {
		// this.maxScaleValue = maxScaleValue;
	}

	/**
	 * 设置当前刻度尺的刻度值,并滚动到相应的位置
	 * 
	 * @param scaleValue
	 */
	public void setScaleScroll(float scaleValue) {

		int scrollX = (int) ((scaleValue - 1.0f) * gap * unit);

		horizontalScrollView.smoothScrollTo(scrollX, 0);
	}
}
