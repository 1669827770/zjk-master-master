package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import videodemo.R;

/**
 * @desc 帮助信息
 * @auth AISPEECH
 * @date 2016-02-15
 * @copyright aispeech.com
 */
public class HelpInfoAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "Adapter-HelpInfoAdapter";

    public final static int HELP_TYPE_WAKEUP = 0;
    public final static int HELP_TYPE_MUSIC = 1;
    public final static int HELP_TYPE_PHONE = 2;
    public final static int HELP_TYPE_NAVIGATION = 3;
    public final static int HELP_TYPE_FM = 4;
    public final static int HELP_TYPE_WEATHER = 5;
    public final static int HELP_TYPE_VEH = 6;
    public final static int HELP_TYPE_STOCK = 7;
    public final static int HELP_TYPE_NEARBY = 8;
    public final static int HELP_TYPE_TRAFFIC = 9;
    public final static int HELP_TYPE_CHAT = 10;
    public final static int HELP_TYPE_WECHAT = 11;

    private Context mContext;

    int[] typeIcons = {
            R.mipmap.tips_apps,
            R.mipmap.tips_navi,
            R.mipmap.tips_phont,
            R.mipmap.tips_music,
            R.mipmap.tips_wechat,
            R.mipmap.tips_weather,
            R.mipmap.help_name,
            R.mipmap.tips_setting,
    };

    private String[] mTypeStrings;
    private ArrayList<String[]> mTipsList = new ArrayList<>();

    public HelpInfoAdapter(Context context) {
        this.mContext = context;
//        initTime();

        String[] mTipsOpenApp = mContext.getResources().getStringArray(R.array.help_type_app);
        String[] mTipsNavi = mContext.getResources().getStringArray(R.array.help_type_navi);
        String[] mTipsPhone = mContext.getResources().getStringArray(R.array.help_type_phone);
        String[] mTipsMusic = mContext.getResources().getStringArray(R.array.help_type_music);
        String[] mTipsWechat = mContext.getResources().getStringArray(R.array.help_type_wechat);
        String[] mTipsWeather = mContext.getResources().getStringArray(R.array.help_type_weather);
        String[] mTipsFM = mContext.getResources().getStringArray(R.array.help_type_fm);
        String[] mTipsNear = mContext.getResources().getStringArray(R.array.help_type_near);
        String[] mTipsVoice = mContext.getResources().getStringArray(R.array.help_type_voice);

        mTypeStrings = mContext.getResources().getStringArray(R.array.help_type_array);

        mTipsList.add(mTipsOpenApp);
        mTipsList.add(mTipsNavi);
        mTipsList.add(mTipsPhone);
        mTipsList.add(mTipsMusic);
        mTipsList.add(mTipsWechat);
        mTipsList.add(mTipsWeather);
        mTipsList.add(mTipsFM);
        mTipsList.add(mTipsNear);


    }

    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mTipsList.get(groupPosition)[childPosition];
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_help_children, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text_child);
        tv.setText(mTipsList.get(groupPosition)[childPosition]);
        return convertView;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mTipsList.get(groupPosition).length;
    }

    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return mTipsList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mTipsList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //设置父item组件
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHodlerParent viewHodlerParent;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_help_parent, null);
            viewHodlerParent = new ViewHodlerParent();
            viewHodlerParent.typeImage = (ImageView) convertView.findViewById(R.id.image_type);
            viewHodlerParent.typeText = (TextView) convertView.findViewById(R.id.text_type);
            viewHodlerParent.imageIndicator = (ImageView) convertView.findViewById(R.id.image_indicator);
            convertView.setTag(viewHodlerParent);
        } else {
            viewHodlerParent = (ViewHodlerParent) convertView.getTag();
        }
        viewHodlerParent.typeImage.setImageResource(typeIcons[groupPosition]);
        viewHodlerParent.typeText.setText(mTypeStrings[groupPosition]);

        if (isExpanded) {
            viewHodlerParent.imageIndicator.setImageResource(R.mipmap.icon_arrow_up);
        } else {
            viewHodlerParent.imageIndicator.setImageResource(R.mipmap.icon_arrow_down);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 2016;
                try {
                    URL url = new URL("http://www.baidu.com");
                    URLConnection uc = url.openConnection();// 生成连接对象
                    uc.connect(); // 发出连接
                    long ld = uc.getDate(); // 取得网站日期时间
                    Date date = new Date(ld); // 转换为标准时间对象
                    // 分别取得时间中的小时，分钟和秒，并输出
                    time = date.getYear() + 1900;
                } catch (Exception e) {
                    e.printStackTrace();
                }// 取得资源对象
                if (time >= 2016) {//如果网络获得的时间大于等于2016
//                    PreferenceHelper.getInstance().setTime(time);
                }
            }
        }).start();
    }

    class ViewHodlerParent {
        ImageView typeImage;
        TextView typeText;
        ImageView imageIndicator;
    }
}
