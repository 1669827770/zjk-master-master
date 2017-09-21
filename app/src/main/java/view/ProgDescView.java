package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import videodemo.R;


public class ProgDescView extends LinearLayout {

	private TextView tvTitle;
	private TextView tvLeft;
	private TextView tvRight;
	private ProgressBar pbPdv;

	public ProgDescView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ProgDescView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public ProgDescView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化布局view
	 */
	private void initView() {
		// 初始化view 并指定父view
		View.inflate(getContext(), R.layout.prog_desc_view, this);
//		View view = View.inflate(getContext(), R.layout.prog_desc_view, null);
//		addView(view);

		tvTitle = (TextView) findViewById(R.id.tv_pdv_title);
		pbPdv = (ProgressBar) findViewById(R.id.pb_pdv);
		tvLeft = (TextView) findViewById(R.id.tv_pdv_left);
		tvRight = (TextView) findViewById(R.id.tv_pdv_right);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 设置左边的文字
	 * 
	 * @param left
	 */
	public void setLeftText(String left) {
		tvLeft.setText(left);
	}

	/**
	 * 设置左边的文字
	 * 
	 * @param left
	 */
	public void setRightText(String right) {
		tvRight.setText(right);
	}

	/**
	 * 设置进度
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		pbPdv.setProgress(progress);
	}

}
