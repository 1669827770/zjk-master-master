package view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017-07-04.
 */

public class OneFlowLayout  extends ViewGroup {

    /**
     * 因为只在代码中直接new，所以创建这个构造方法就可以了
     */
    public OneFlowLayout(Context context) {
        super(context);
    }


    /**
     * 测量容器（FlowLayout）自身的宽高，并且测量子View的宽高
     * onMeasure方法可能会被调用多次
     *
     * @param widthMeasureSpec  父容器希望FlowLayout的宽
     * @param heightMeasureSpec 父容器希望FlowLayout的高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		MeasureSpecUtil.printMeasureSpec(widthMeasureSpec, heightMeasureSpec);
// 这个方法的作用就是打印一下容器的测量规格，里面的参数就是方法onMeasure方法中的参数，看下面的图一，发现父容器的高测量规格是未指定，宽是没有问题的，什么情况下会传一个未指定呢？是因为包裹父容器（指的flowlayout）的容器是可以滚动的容器(flowlayout被包裹在了scrollview中)，


        //	获取容器的宽，其实就是上面打印的的那个
        int containerMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        // 遍历所有的子View
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            // 创建一个未指定的测量规格
            int unspecifiedMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);  // 这个里面的第二个参数是未指定，那么导致第一个参数传什么值都没用，因为已经是为未指定了，传参数就么有用了，这两参数说一下，第一个是传测量值，第二个是测量模式
            // 以前学的view.mesure(0,0)，其实这里的0就是MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)的返回值，即传0就是未指定，不限定宽高
            // 把未指定的测量规格传给子View，让子View自己完成测量工作，textview人家自己已经实现了onmesure测量方法，所以你只要将测量规格传给textview,人家自己就会调用onMeasure进行测量
            child.measure(unspecifiedMeasureSpec, unspecifiedMeasureSpec);  //子view测量自己，//measure方法里面的蚕食测量规格，测量规格怎么创建呢，测量调用的measure方法，但是其内部调用的是onMeasure,排版也是调用的layout,但是内部调用的onLayout
            // 指定容器的高为子view的高，即textvew的高
            int containerMeasuredHeight = getChildAt(0).getMeasuredHeight();
            // 这个方法用于保存测量之后的宽和高   （在这里保存了，才能在onLayout中取出测量的宽高，所谓的保存就是赋值成了成员变量，见图二）
            setMeasuredDimension(containerMeasuredWidth, containerMeasuredHeight);
        }
    }


        // 对FlowLayout的子View进行排版
        @Override
        protected void onLayout ( boolean changed, int l, int t, int r, int b){
            // 获取子View(前面在activity中已经添加将textview添加到了flowlayout中了，所以可以通过getchildAT获取到textview，即子view)
            View child = getChildAt(0);

            // 获取子View的宽和高（在onlayout中获取不到view的宽高的，因为宽高是有onlayout的上下左右决定的，此时只能获取到测量的宽高，测量完的宽高并不是最终的宽高，排版完才是最终的宽高），但是这里要获取测量的宽高，你的先测量，所以我们还得复写onMeasure方法
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            System.out.println("childWidth = " + childWidth + ", childHeight = " + childHeight);

            // 设置子View在容器中的位置
            int childLeft = 0;   // 因为是从左上角开始的，所有左上就是0,0，如果是从别的位置开始，修改位置即可
            int childTop = 0;
            int childRight = childLeft + childWidth;
            int childBottom = childTop + childHeight;


            child.layout(childLeft, childTop, childRight, childBottom);
        }

    }

