package interfaces;

import android.view.View;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface UiInterface {

    /** 返回当前 Activity 使用的布局 */
     int getLayoutID();

    /** 所有的 findViewById 操作必须在这个方法处理 */
     void initView();

    /** 注册监听器、适配器、广播接收者 */
     void initListener();

    /** 获取数据，初始化界面 */
     void initData();

    /** 在Base没有处理的点击事件，在这个方法统一处理 */
     void processClick(View view);
}
