package base.impl;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import base.BasePager;


public class GovaffairsPager extends BasePager {

	public GovaffairsPager(Context context) {
		super(context);
	}

	/**
	 * 根据自己的界面更新父类里面的控件
	 */
	@Override
	public void initData() {
		System.out.println("政务加载了");
		// 更新标题 隐藏左侧按钮
		tv_basepager_title.setText("政务");
		
		// 给自己添加内容
		TextView textView = new TextView(mContext);
		textView.setGravity(Gravity.CENTER);
		textView.setText("政务");
		// 把之前添加到控件删除
				fl_basepager_content.removeAllViews();
		fl_basepager_content.addView(textView);
	}
}
