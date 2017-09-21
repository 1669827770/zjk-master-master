package view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {
	
	private int horizotalSpacing = 6;
	private int verticalSpacing = 6;
	
	/** 这个集合用于保存所有的行 */
	private ArrayList<ArrayList<View>> allLines = new ArrayList<ArrayList<View>>();

	public FlowLayout(Context context) {
		super(context);
	}
	
	/**
	 * 测量容器自身的宽高，并且测量子View的宽高
	 * onMeasure方法可能会被调用多次
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		MeasureSpecUtil.printMeasureSpec(widthMeasureSpec, heightMeasureSpec);
		
		allLines.clear();
		
		int containerMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);
		
		ArrayList<View> oneLine = null;	// 这个集合用于装一行的View
		
		// 遍历所有的子View
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			
			// 创建一个未指定的测量规格
			int unspecifiedMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			// 以前学的view.mesure(0,0)，其实这里的0就是MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)的返回值
			
			// 把未指定的测量规格传给子View，让子View自己完成测量工作
			child.measure(unspecifiedMeasureSpec, unspecifiedMeasureSpec);
			
			if (i == 0 || child.getMeasuredWidth() > getUsableWidth(containerMeasuredWidth, oneLine)) {
				// 如果是第一个子View，或者子View的宽大于一行中可用的宽，则需要用一个新的行来存放
				oneLine = new ArrayList<View>();
				allLines.add(oneLine);
			}
			
			oneLine.add(child);	// 把子View放入一行中
		}
		
		int totalVerticalPadding = getPaddingTop() + getPaddingBottom();	// 垂直方向总的padding
		int totalVerticalSpacing = verticalSpacing * (allLines.size() -1);
		// 容器的高为所有行的高加上总垂直padding，再加上总的垂直间距
		int containerMeasuredHeight = getChildAt(0).getMeasuredHeight() * allLines.size() + totalVerticalPadding + totalVerticalSpacing;
		
		// 这个方法用于保存测量之后的宽和高 
		setMeasuredDimension(containerMeasuredWidth, containerMeasuredHeight); 
	}

	/**
	 * 获取一行中剩余可以的宽
	 * @param containerMeasuredWidth
	 * @param oneLine
	 * @return
	 */
	private int getUsableWidth(int containerMeasuredWidth, ArrayList<View> oneLine) {
		
		// 计算oneLine中所有子View的宽的总和
		int oneLineTotalWidth = 0;
		for (int i = 0; i < oneLine.size(); i++) {
			View child = oneLine.get(i);
			oneLineTotalWidth = child.getMeasuredWidth() + oneLineTotalWidth;
		}
		
		// 计算水平方向总的padding
		int tatalHorizotalPadding = getPaddingLeft() + getPaddingRight();
		
		// 计算水平方向总的spacing
		int totalHorizotalSpacing = horizotalSpacing * (oneLine.size() - 1);
		
		// 可用的宽为容器的宽减去当前行已存在的所有子View的宽，再减水平方向总的adding，再减水平总向总的间距
		int usableWidth = containerMeasuredWidth - oneLineTotalWidth - tatalHorizotalPadding - totalHorizotalSpacing;
		return usableWidth;
	}

	/**
	 * 用于安排子View的位置
	 * @param l、t、r、b是 相对于容器左上角为0，0点的位置
	 * 如果不用跟右边的对其，就将下面的带*号的两处单行注释打开就行了
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int tempRight = 0;	// 这个变量用于临时保存上一个View的right位置
		int tempBottom = 0;	// 这个变量用于临时保存上一行View的Bottom位置
		
		// 遍历多行
		for (int rowIndex = 0; rowIndex < allLines.size(); rowIndex++) {
			ArrayList<View> oneLine = allLines.get(rowIndex);	// 取出一行
			
			// 计算每个子View可以平均分得的剩余宽
//*			int  averageUsableWidth = getUsableWidth(getMeasuredWidth(), oneLine) / oneLine.size();
			
			// 遍历一行中的所有子View
			for (int columnIndex = 0; columnIndex < oneLine.size(); columnIndex++) {
				View child = oneLine.get(columnIndex);
				
				int childMeasuredWidth = child.getMeasuredWidth();	// 获取子View测量之后的宽
				int childMeasuredHeight = child.getMeasuredHeight();// 获取子View测量之后的高
				
				// 如果是第0个View，则View的left为位置为getPaddingLeft()，其它的View的left位置为上一个View的right一样
				int childLeft = columnIndex == 0 ? getPaddingLeft() : tempRight + horizotalSpacing;
				// 如果是第0行View，则View的top位置为getPaddingTop()，其它的行的View的top为上一行View的bottom一样
				int childTop = rowIndex == 0 ? getPaddingTop() : tempBottom + verticalSpacing;
				
				// 获取paddingRight所在的位置 = 容器宽 - getPaddingRight
				int paddingRightCoord = getMeasuredWidth() - getPaddingRight();
				
				int childRight =childLeft + childMeasuredWidth;	// View的left + View的宽 + 平均可以宽度


//	*			int childRight = columnIndex == oneLine.size() - 1 // 如果是一行中最后一个View，
//						? paddingRightCoord 	// right跟paddingRight的位置对齐
//						: childLeft + childMeasuredWidth + averageUsableWidth;	// View的left + View的宽 + 平均可以宽度


				int childBottom = childTop + childMeasuredHeight;
				
				// 按排子View的位置
				child.layout(childLeft, childTop, childRight, childBottom);
				
				tempRight = childRight; //保存当前View的right
				
				// TextView字体的居中属性是在onMeasure方法中算出来的，现在我们改变TextView的宽，我们要想让居中位置重新计算一下，则重测量一下
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childRight - childLeft, MeasureSpec.EXACTLY);
				int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childBottom - childTop, MeasureSpec.EXACTLY);
				child.measure(widthMeasureSpec, heightMeasureSpec);	// 重新测量，让居中属性重新计算一下
			}
			
			tempBottom = oneLine.get(0).getBottom();	// 保存当前行的Bottom位置
		}
		
	}

}
