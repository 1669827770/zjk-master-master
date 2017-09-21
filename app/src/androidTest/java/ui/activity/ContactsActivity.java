package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.MyAdapter;
import bean.GoodMan;
import util.Cheeses;
import videodemo.R;
import view.QuickIndexBar;

/**
 * Created by admin on 2017-07-07.
 */

public class ContactsActivity  extends Activity {

    private QuickIndexBar quickIndexBar;
    private TextView tv_showLetter;
    private ListView listView;
    private List<GoodMan> goodManList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //找控件
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        tv_showLetter = (TextView) findViewById(R.id.tv_showLetter);
        listView = (ListView) findViewById(R.id.listView);


        //给listview设置适配器
        goodManList = new ArrayList<GoodMan>();
        for (String name : Cheeses.NAMES) {
            goodManList.add(new GoodMan(name));
        }
        //排序
        Collections.sort(goodManList);
        listView.setAdapter(new MyAdapter(this, goodManList));

        //索引条
        quickIndexBar.setOnLetterChangedListener(onLetterChangedListener);
    }


    private Handler handler=new Handler();
    private QuickIndexBar.OnLetterChangedListener onLetterChangedListener=new QuickIndexBar.OnLetterChangedListener() {
        @Override
        public void letterChanged(String letter) {
            //显示和设置文本
            tv_showLetter.setVisibility(View.VISIBLE);
            tv_showLetter.setText(letter);
            handler.postDelayed(showLetterTask,2000);  //2秒后消失


            //----------------------优雅的分割线：定位到listview的相应条目上面---------------------
            for (int i = 0; i < goodManList.size(); i++) {    //这里我觉得有点耗性能，应该直接传递个position过来，直接定位
                GoodMan goodMan = goodManList.get(i);
                String firstLetter = goodMan.getPinyin().charAt(0) + "";
                if (TextUtils.equals(letter,firstLetter)){
                    listView.setSelection(i);
                    break;
                }
            }
        }

    };

    private Runnable showLetterTask=new Runnable() {
        @Override
        public void run() {
            tv_showLetter.setVisibility(View.INVISIBLE);
        }
    };
}
