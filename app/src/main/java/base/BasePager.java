package base;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import videodemo.R;

/**
 * 正文界面5个页面的基类
 * @author h
 *
 */
public abstract class BasePager implements OnClickListener {
	
	protected Context mContext;// 通过构造方法传递进上下文对象，让子类创建布局,传进来的其实还是MainUI对象
	public View rootView;// 对象一创建，就创建布局对象，把布局作为成员变量，rootView一直保存才pager对象身上
	protected ImageButton ib_basepager_menu;
	protected TextView tv_basepager_title;
	protected FrameLayout fl_basepager_content;
	protected ImageButton ib_titlebar_photo;

	public BasePager(Context context){
		this.mContext = context;
		rootView = initView();
	}
	
	/**
	 * 子类必须实现，返回自己的控件或布局
	 * 因为5个界面有大致一样的结构，所以把界面抽取到BasePager中
	 * @return
	 */
	public View initView(){
		View view = View.inflate(mContext, R.layout.basepager, null);
		ib_basepager_menu = (ImageButton) view.findViewById(R.id.ib_basepager_menu);
		tv_basepager_title = (TextView) view.findViewById(R.id.tv_basepager_title);
		fl_basepager_content = (FrameLayout) view.findViewById(R.id.fl_basepager_content);
		ib_titlebar_photo = (ImageButton) view.findViewById(R.id.ib_titlebar_photo);
		ib_basepager_menu.setOnClickListener(this);
		return view;
	}
	
	/**
	 * 子类更新布局，不必须实现
	 */
	public void initData(){
		
	}
	/**
	 * 5个界面中如果显示菜单按钮，点击后自动开关菜单
	 */
	@Override
	public void onClick(View v) {
//		MainUI mainUI = (MainUI) mContext;
//		mainUI.getSlidingMenu().toggle();
	}
	
}
