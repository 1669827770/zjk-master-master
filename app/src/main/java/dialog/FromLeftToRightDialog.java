package dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import videodemo.R;


public class FromLeftToRightDialog extends Dialog {

    private ListView lvStyle1;
    TextView tv_cancel;

    private ListView lvStyle;

    private static final String[] mTitles = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰",
            "苹果绿"};

    public FromLeftToRightDialog(Context context) {

        super(context, R.style.AddrDialogStyleRight);

        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    public FromLeftToRightDialog(Context context, int style) {

        super(context, style);
//        getWindow().setGravity(Gravity.BOTTOM);
//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();
//        LayoutParams p = getWindow().getAttributes();
//        p.width = d.getWidth();
//        getWindow().setAttributes(p);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addr2);
        lvStyle1 = (ListView) findViewById(R.id.lv_addr_style1);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        lvStyle1.setAdapter(new StyleAdapter());
        lvStyle1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               dismiss();

            }
        });
    }




    public class StyleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mTitles.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_addr_style, null);
            }
            ImageView ivBg = (ImageView) convertView.findViewById(R.id.iv_ias_bg);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_ias_name);
            ImageView ivSelect = (ImageView) convertView.findViewById(R.id.iv_ias_select);
            tvName.setText(mTitles[position]);
            return convertView;
        }

    }
}


