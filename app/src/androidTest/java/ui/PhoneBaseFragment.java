package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import interfaces.UiInterface;
import util.PhoneLogUtils;
import videodemo.R;


/**
 * Created by Administrator on 2016/8/12.
 */
public abstract class PhoneBaseFragment extends Fragment implements View.OnClickListener, UiInterface {

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getLayoutID(),null);

        // 查找控件
        initView();
        // 注册监听
        initListener();
        // 获取数据，填充内容
        initData();

        // 注册多个界面共享的按钮的点击监听
        registerCommonButton();
        return root;
    }

    /** 注册多个界面共享的按钮的点击监听*/
    protected void registerCommonButton() {
        View view = findViewById(R.id.back);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    /** 自定义 findViewById,方便今后的使用 */
    protected View findViewById(int id){
        return root.findViewById(id);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.back:
                getFragmentManager().popBackStack();
                break;
            default:
                processClick(view);
                break;
        }
    }

    /** 显示内容为 msg 的吐司 */
    protected void toast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /** 打印一个 error 等级的log */
    protected void logE(String msg){PhoneLogUtils.e(getClass(), msg);
    }
}
