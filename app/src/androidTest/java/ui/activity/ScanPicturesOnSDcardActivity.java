package ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import util.FileUtil;
import util.SDCardUtils;
import videodemo.R;

/**
 * Created by admin on 2017-07-18.
 */

public class ScanPicturesOnSDcardActivity extends Activity {


    private ArrayList<String> imgPath = new ArrayList<String>();   //定义一个数组用于保存文件路径

    private static String[] imageFormat = new String[]{"jpg", "bmp", "gif"};      //定义图片格式
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanpicturesonsdcard);
        initView();

        /**
         * 保存图片返回路径
         */
        //Android中 Bitmap和Drawable相互转换的方法   1.Drawable—>Bitmap
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.slide_background2);
        String s = FileUtil.saveBitmap(bmp);
        mTv6.setText(s);


        if (SDCardUtils.isSDCardEnable()) {   //判断SD卡存在
            String path = Environment.getExternalStorageDirectory().getPath();
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String path1 = getApplication().getFilesDir().getPath();
            File file = Environment.getExternalStorageDirectory();//获得SD卡根目录路径
            String state = Environment.getExternalStorageState();//获得SD卡的状态
            mTv1.setText("Environment.getExternalStorageDirectory().getPath()=" + path);
            mTv2.setText("getApplication().getFilesDir().getPath()=" + path1);
            mTv3.setText("Environment.getExternalStorageDirectory().getAbsolutePath()=" + absolutePath);

            getSdCardImgFile(path);
            mTv4.setText("数量" + imgPath.size());
        }


/**
 *  获取sd卡可用空间
 */
        //获取sd卡的路径
        File file = Environment.getExternalStorageDirectory();
        //external     外部的；  Storage n. 存储；
        //获取SD卡的总空间  Total 全部的
        long totalSpace = file.getTotalSpace();  //这需要来一个压制警告
        //获取SD卡卡可用空间   Usable可用的
        long usableSpace = file.getUsableSpace();
        //生成的是long类型的,显示的时候需要转化为string类型的,转换成多少个G,符合阅读习惯
        //这个里面的第一个参数还是上下文,所以用this
        String total = Formatter.formatFileSize(this, totalSpace);
        String usable = Formatter.formatFileSize(this, usableSpace);
       /*拿到大小之后就显示在手机屏上 ,这个tv_info就是在清单文件中testview中设置
        * id的时候的tv_info,而setText方法是testview的方法,按Ctrl+t就知道*/
        mTv5.setText("total:::" + total + "usable:::" + usable);





    }


    private boolean isImageFile(String path) {                                       //判断是否为图片文件的方法
        for (String format : imageFormat) {
            if (path.contains(format)) {                                 //如果文件名字包含定义的格式后缀，则返回true
                return true;
            }
        }
        return false;
    }

    private void getSdCardImgFile(String url) {         //获取指定路径下的指定格式图片文件，传入路径参数
        File files = new File(url);       //新定义一个文件，路径则为传入的url
        File[] file = files.listFiles();          //遍历该文件所有的子文件夹生成文件夹数组
        for (File f : file) {              //for循环遍历到文件数组
            if (f.isDirectory()) {            //如果为文件夹，则递归调用此方法遍历子文件夹
                getSdCardImgFile(f.getAbsolutePath());  //递归调用
            } else {
                if (isImageFile(f.getPath())) {  //如果文件是图片文件

                    imgPath.add(f.getAbsolutePath());//获取绝对路径，返回到定义好的数组中。
                }
            }
        }
    }

    private void initView() {
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
    }
}
