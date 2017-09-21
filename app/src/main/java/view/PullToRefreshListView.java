package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import videodemo.R;


public class PullToRefreshListView extends ListView implements OnScrollListener{

	private int headermeasuredHeight;
	private RotateAnimation up;
	private RotateAnimation down;
	private int downY;
	private View headerview;
	// 下拉刷新
	public static final int PULL_DOWN = 1;
	// 松开刷新
	public static final int RELEASE_REFRESH = 2;
	// 正在刷新
	public static final int REFRESHING = 3;
	// 当前的状态,默认下拉刷新
	public int CURRENTSTATE = 1;
	private TextView text;
	private ProgressBar pb;
	private ImageView arrow;
	
	/**
	 *标示是否是加载更多操作，true：加载更多，false:没有加载更多
	 */
	private boolean isLoadMore=false;

	public PullToRefreshListView(Context context) {
		// super(context);
		this(context, null);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		this(context, attrs, 0);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		setHeader();
		setFooter();
		//设置listview的滚动监听
		setOnScrollListener(this);
	}
	/**
	 * 添加底部条目的操作
	 */
	private void setFooter() {
		footerView = View.inflate(getContext(), R.layout.footerview, null);
		
		//隐藏加载更多条目
		footerView.measure(0, 0);
		footermeasuredHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, 0, 0, -footermeasuredHeight);
		
		addFooterView(footerView);
	}
	
	/**
	 * 添加刷新头
	 */
	private void setHeader() {
		headerview = View.inflate(getContext(), R.layout.headerview, null);

		arrow = (ImageView) headerview.findViewById(R.id.arrow);
		pb = (ProgressBar) headerview.findViewById(R.id.progreesbar);
		text = (TextView) headerview.findViewById(R.id.text);

		// 隐藏刷新头
		// 设置刷新头距离边框的距离
		// 获取测量的宽高
		headerview.measure(0, 0);
		headermeasuredHeight = headerview.getMeasuredHeight();
		headerview.setPadding(0, -headermeasuredHeight, 0, 0);

		addHeaderView(headerview);// 在listview的头部添加一个条目

		// 初始化箭头动画
		initAnimation();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int measuredHeight = headerview.getMeasuredHeight();
		Log.i("AAA", "........................."+measuredHeight);
		
		
		super.onLayout(changed, l, t, r, b);
		
	}

	/**
	 * 初始化动画
	 */
	private void initAnimation() {
		// 旋转动画
		// 箭头向上的动画
		up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		up.setDuration(500);// 设置动画的持续时间
		up.setFillAfter(true);// 设置保持动画结束的位置
		// 箭头向下的动画
		down = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		down.setDuration(500);// 设置动画的持续时间
		down.setFillAfter(true);// 设置保持动画结束的位置
	}

	// 触摸listview，实现下拉显示刷新头的操作
	// 1.下拉
	// 2.当前界面显示的第一个条目必须是listview的第一个条目
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int distance = (int) (ev.getY() - downY);
			if (distance > 0 && getFirstVisiblePosition() == 0) {
				// 获取显示空白区域，让listview能够展示空白区域
				int paddingtop = distance - headermeasuredHeight;
				if(paddingtop>headermeasuredHeight){
					paddingtop=headermeasuredHeight;
				}
				// 显示刷新头
				headerview.setPadding(0, paddingtop, 0, 0);

				// 下拉距离超过头布局，也就是是头布局完全展示出来，显示松开刷新
				if (paddingtop > 0 && CURRENTSTATE == PULL_DOWN) {
					CURRENTSTATE = RELEASE_REFRESH;
					switchSate();
				}
				// 下拉距离没有超过头布局，也就是是头布局没有完全展示出来就，显示下拉刷新
				if (paddingtop < 0 && CURRENTSTATE == RELEASE_REFRESH) {
					CURRENTSTATE = PULL_DOWN;
					switchSate();
				}

				// 拦截系统的触摸操作，使用自己的计算的空白作为触摸操作的实现
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			// 松开刷新 -> 正在刷新 ，并且显示刷新头
			if (CURRENTSTATE == RELEASE_REFRESH) {
				CURRENTSTATE = REFRESHING;
				switchSate();
				headerview.setPadding(0, 0, 0, 0);
				//加载数据的操作
				if (onPullToRefreshListener != null) {
					onPullToRefreshListener.refrsh();
				}
			}
			// 下拉刷新 -> 隐藏刷新头
			if (CURRENTSTATE == PULL_DOWN) {
				headerview.setPadding(0, -headermeasuredHeight, 0, 0);
			}
			break;
		}
		// 因为如果不是显示空白区域，还是要执行系统的触摸操作来实现下拉操作，所以这里不能直接改成return true
		return super.onTouchEvent(ev);
	}

	/**
	 * 根据状态改变控件的内容
	 */
	private void switchSate() {
		switch (CURRENTSTATE) {
		case PULL_DOWN:
			text.setText("下拉刷新");
			arrow.startAnimation(down);
			break;
		case RELEASE_REFRESH:
			text.setText("松开刷新");
			arrow.startAnimation(up);
			break;
		case REFRESHING:
			text.setText("正在刷新");
			arrow.clearAnimation();//清除动画
			arrow.setVisibility(View.GONE);//设置控件的显示隐藏操作
			pb.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	private OnPullToRefreshListener onPullToRefreshListener;
	private View footerView;
	private int footermeasuredHeight;
	//2.创建接受接口对象的方法
	public void setOnPullToRefreshListener(OnPullToRefreshListener pullToRefreshListener){
		this.onPullToRefreshListener = pullToRefreshListener;
	}
	
	//1.定义回调接口
	public interface OnPullToRefreshListener{
		/**
		 * 下拉刷新加载数据的
		 */
		public void refrsh();
		/**
		 * 上拉加载更多的操作
		 */
		public void loadmore();
	}
	/**
	 * 取消刷新操作
	 */
	public void finish(){
		if (CURRENTSTATE == REFRESHING) {
			//正在刷新 -> 下拉刷新，并且将刷新头隐藏
			CURRENTSTATE = PULL_DOWN;
			text.setText("下拉刷新");
			pb.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			headerview.setPadding(0, -headermeasuredHeight, 0, 0);
		}
		//取消加载更多的操作
		if (isLoadMore) {
			footerView.setPadding(0, 0, 0, -footermeasuredHeight);
			isLoadMore = false;
		}
	}
	//当listview的滚动状态改变的时候调用
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//OnScrollListener.SCROLL_STATE_IDLE:空闲状态
		//OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:触摸滚动
		//OnScrollListener.SCROLL_STATE_FLING:快速滚动状态
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount()-1 && isLoadMore == false) {
			isLoadMore = true;
			footerView.setPadding(0, 0, 0, 0);
			setSelection(getCount()-1);//定位到listview的哪个条目
			//加载更多数据
			if (onPullToRefreshListener != null) {
				onPullToRefreshListener.loadmore();
			}
		}
	}
	//listview滚动调用的方法
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
