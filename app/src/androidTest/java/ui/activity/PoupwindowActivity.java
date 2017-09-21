package ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import adapter.ListViewAdapter;
import videodemo.R;

/**
 * Created by admin on 2017-05-16.
 */

public class PoupwindowActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private EditText mEdittext;
    private ImageButton mNext;
    private ListView listview;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poupwindow);
        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mEdittext = (EditText) findViewById(R.id.edittext);
        mNext = (ImageButton) findViewById(R.id.next);

        mNext.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                //显示PopupWindow
                showPopupWindow();
                break;

            default:
                break;
        }
    }
    /**
     * 显示PopupWindow
     */
    private void showPopupWindow() {
        View contentView = CreateView();
        //focusable : PopupWindow是否可以获取焦点
        popupWindow = new PopupWindow(contentView, mEdittext.getWidth()-4, 300, true);

        popupWindow.setOutsideTouchable(true);//设置点击空白处是否可以隐藏控件
        //在popuwindow如何想要执行必须动画之类操作，必须设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //显示popupWindow
        //popupWindow.showAsDropDown(mEdittext);//显示在哪个控件下方
        //xoff : 距离Edittext左边框的距离
        //yoff : 距离Edittext顶边框的距离
        popupWindow.showAsDropDown(mEdittext, 2, -5);
    }
    /**
     * 获取一个ListView的布局文件转化的view对象
     * @return
     */
    private ListView CreateView() {
        listview = (ListView) View.inflate(this, R.layout.popupwindow_item, null);
        listview.setOnItemClickListener(this);
        //给listview添加数据
        listview.setAdapter(new ListViewAdapter(this));

        return listview;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //获取数据，将数据保存到edittext中，隐藏popuwindow
        String text = (String) listview.getItemAtPosition(position);//根据条目的位置，获取条目对应的数据，类似list.get(position)
        mEdittext.setText(text);
        popupWindow.dismiss();
    }
}

