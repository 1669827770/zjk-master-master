package view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import videodemo.R;

public class AddrToast {
		private Context mContext;
	private WindowManager mWM;
	private View mView;
	private WindowManager.LayoutParams mParams;
	private int startX;
	private int startY;
	private TextView mTvOpentext;

	public AddrToast(Context context) {
		super();
		this.mContext = context;

	}

	/**
	 * 显示
	 *
	 */
	public void show() {
		// 每次显示之前 如果已经显示 就关掉 防止 多次来电
		hide();

		// Window 窗口 是一个顶级布局 dialog toast activity 都是显示到窗口上
		// 获取窗口管理器 可以管理窗口的操作 比如 添加一个显示的view
		mWM = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mView = View.inflate(mContext, R.layout.activity_agreement, null);
		mTvOpentext = (TextView) mView.findViewById(R.id.tv_opentext);

		mTvOpentext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				hide();
			}
		});



		// LayoutParams 布局参数 在布局文件里以android:layout_开头的 都可以通过LayoutParams 在代码中设置
		mParams = new WindowManager.LayoutParams();
		mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
		mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		mParams.format = PixelFormat.TRANSLUCENT;
		// 更改显示的级别 才能触摸 权限 android.permission.SYSTEM_ALERT_WINDOW
		mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		mParams.setTitle("Toast");

		mParams.gravity= Gravity.CENTER;

		// 给window添加一个view显示
		mWM.addView(mView, mParams);
	}

	/**
	 * 隐藏
	 */
	public void hide() {
		if (mView != null) {
			// note: checking parent() just to make sure the view has
			// been added... i have seen cases where we get here when
			// the view isn't yet added, so let's try not to crash.
			if (mView.getParent() != null) {
				// 移除view 不显示
				mWM.removeView(mView);

			}
			mView = null;
		}
	}


}
