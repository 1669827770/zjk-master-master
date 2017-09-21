package ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import interfaces.UiInterface;
import util.PhoneLogUtils;
import videodemo.R;


/**
 * 1. 规范代码结构
 * 2. 提供在多个界面都使用的方法，精简子类的代码
 * AppComaptActivity，这个类是ActionBarActivity的一个替代品，
 * 这个类用于向下兼容ActionBar，这个类要求主题必须使用Theme.AppComat或者它的子类主题
 */
public abstract class PhoneBaseActivity extends FragmentActivity implements View.OnClickListener, UiInterface {

    @Override
    /** 子类禁止覆写 onCreate 方法 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置布局文件
        setContentView(getLayoutID());
        // 查找控件
        initView();
        // 注册监听
        initListener();
        // 获取数据，填充内容
        initData();

        // 注册多个界面共享的按钮的点击监听
        registerCommonButton();
    }

    /** 注册多个界面共享的按钮的点击监听*/
    protected void registerCommonButton() {
        View view = findViewById(R.id.back);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.back:
                finish();
                break;
            default:
                processClick(view);
                break;
        }
    }

    /** 显示内容为 msg 的吐司 */
    protected void toast(String msg){
        Toast.makeText(PhoneBaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /** 打印一个 error 等级的log */
    protected void logE(String msg){
        PhoneLogUtils.e(getClass(), msg);
    }
}
