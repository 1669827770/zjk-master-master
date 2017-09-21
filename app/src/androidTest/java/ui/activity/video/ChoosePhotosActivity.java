package ui.activity.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import util.ActivityManagerUtil;
import videodemo.R;

/**
 * Created by admin on 2017/2/13.
 */

public class ChoosePhotosActivity extends FragmentActivity implements View.OnClickListener{


    Button mchose_photo;
    Button mchose_video;
    ChooseVideoFragment chooseVideoFragment;
//    ChoosePhotosFragment choosePhotosFragment;
    FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_choose_photo_topic);

/**
 * getSupportFragmentManager()  没定义的问题时，修改下activity为
 FragmentActivity或者AppCompatActivity。
 */
        supportFragmentManager = getSupportFragmentManager();

        // 初始化布局元素
        initViews();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        ActivityManagerUtil.addActivity(ChoosePhotosActivity.this,"ChoosePhotosActivity");
    }


    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        mchose_photo = (Button) findViewById(R.id.chose_photo);
        mchose_video = (Button) findViewById(R.id.chose_video);

        mchose_photo.setOnClickListener(this);
        mchose_video.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chose_photo:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.chose_video:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
//                mchose_photo.setTextColor(Color.rgb(24, 195, 194));
//                if (choosePhotosFragment == null) {
//                    // 如果MessageFragment为空，则创建一个并添加到界面上
//                    choosePhotosFragment = new ChoosePhotosFragment();
//                    fragmentTransaction.add(R.id.content, choosePhotosFragment);
//                } else {
//                    // 如果MessageFragment不为空，则直接将它显示出来
//                    fragmentTransaction.show(choosePhotosFragment);
//
//                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mchose_video.setTextColor(Color.rgb(24, 195, 194));
                if (chooseVideoFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    chooseVideoFragment = new ChooseVideoFragment();
                    fragmentTransaction.add(R.id.content, chooseVideoFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(chooseVideoFragment);
                }
                break;

            default:

                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mchose_photo.setTextColor(Color.parseColor("#2b2b2b"));
        mchose_video.setTextColor(Color.parseColor("#2b2b2b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
//        if (choosePhotosFragment != null) {
//            transaction.hide(choosePhotosFragment);
//        }
        if (chooseVideoFragment != null) {
            transaction.hide(chooseVideoFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

//    @Override
//    public void loadInitView(ChooseAlbumAdater adater) {
//
//    }
//
//    @Override
//    public void loadAlbumUi(ChooseAlbumListAdapter adapter, boolean albumHasOpen) {
//
//    }
//
//    @Override
//    public void changeAlbum(ChooseAlbumAdater adater) {
//
//    }
}


