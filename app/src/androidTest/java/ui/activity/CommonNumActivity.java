package ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import db.CommonNumDAO;
import videodemo.R;


public class CommonNumActivity extends Activity {

	private ExpandableListView elCn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_num);
		elCn = (ExpandableListView) findViewById(R.id.el_cn);

		// 填充数据
		elCn.setAdapter(new ElAdapter());

		// 组的点击事件
		elCn.setOnGroupClickListener(new OnGroupClickListener() {

			private int selectePosition = -1;

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				// 判断当前点击的这条是不是已经展开
				if (elCn.isGroupExpanded(groupPosition)) {
					// 已经展开 去关闭
					elCn.collapseGroup(groupPosition);
				} else {
					// 展开之前 关闭上一个打开的位置
					elCn.collapseGroup(selectePosition);
					// 展开一条
					elCn.expandGroup(groupPosition);
					// 记住当前展开的位置
					selectePosition = groupPosition;

					// 展开后置顶
					elCn.setSelection(groupPosition);
				}
				// 如果要自己处理组点击事件就返回true
				return true;
			}
		});

		// 孩子的点击事件
		elCn.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// 获取当前孩子的内容
				String[] child = CommonNumDAO.getChildName(getApplicationContext(),
						groupPosition, childPosition);

				// 隐式调用 拨打电话页面
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + child[1]));
				startActivity(intent);
				return false;
			}
		});
	}

	private class ElAdapter extends BaseExpandableListAdapter {

		// 组的数量
		@Override
		public int getGroupCount() {
			return CommonNumDAO.getGroupCount(getApplicationContext());
		}

		// 组里面孩子的数量
		@Override
		public int getChildrenCount(int groupPosition) {
			return CommonNumDAO.getChildCount(getApplicationContext(), groupPosition);
		}

		// 返回每一组的布局view
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(getApplicationContext());
			}
			TextView tv = (TextView) convertView;
			tv.setPadding(8, 8, 8, 8);
			// 组的背景
			tv.setBackgroundColor(Color.parseColor("#CECBCE"));
			tv.setText(CommonNumDAO.getGroupName(getApplicationContext(), groupPosition));
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(17);
			return convertView;
		}

		// 返回每一组孩子的布局view
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(getApplicationContext());
			}
			TextView tv = (TextView) convertView;
			tv.setPadding(8, 8, 8, 8);
			// 获取孩子的内容并显示
			String[] child = CommonNumDAO.getChildName(getApplicationContext(),
					groupPosition, childPosition);
			tv.setText(child[0] + "\n" + child[1]);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(15);
			return convertView;
		}

		// 孩子是不是可以选择
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		// 是否拥有一个稳定的id 用不到 默认
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

}
