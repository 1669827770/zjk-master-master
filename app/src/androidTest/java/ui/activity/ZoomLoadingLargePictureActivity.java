package ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import videodemo.R;

/**
 * Created by admin on 2017-07-18.
 */

public class ZoomLoadingLargePictureActivity extends Activity {

    private ImageView iv_dog;

    /*	//1.1.获取一个WindowManager对象
    WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
    //1.2调用getDefaultDisplay方法获取Display对象
    Display defaultDisplay = windowManager.getDefaultDisplay();
    //1.3使用Display对象获取屏幕宽高
    Point point = new Point();
    defaultDisplay.getSize(point);
    int screenWindth = point.x;
    int screen_Height = point.y;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomloadinglargepicture);

        iv_dog = (ImageView) findViewById(R.id.iv_dog);
    }


    //加载图片的点击事件
    public void loadPic(View v) {

        //1.获取手机屏幕的分辨率 宽高
        //1.1.获取一个WindowManager对象
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //1.2调用getDefaultDisplay方法获取Display对象
        Display defaultDisplay = windowManager.getDefaultDisplay();
        //1.3使用Display对象获取屏幕宽高
        int screenWindth = defaultDisplay.getWidth();
        int screenHeight = defaultDisplay.getHeight();
        System.out.println(screenWindth+"*"+screenHeight);

        //2。获取要加载的图片的分辨率 宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 如果设置成true，下面使用decodeResource方法获取Bitmap时返回null,但是仍然会获取图片在内存中所分配的像素（宽高）
        options.inJustDecodeBounds =true ;
        //res:资源对象  id:图片资源id
        BitmapFactory.decodeResource(getResources(), R.drawable.tomcat,options);
        //获取图片宽高
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        System.out.println(imgWidth+"*"+imgHeight);

        //3.计算宽高的压缩比， android中压缩图片，宽高只能按照一个数值进行压缩。 我们需要采用压缩比大的进行压缩
        int widthScale = imgWidth/screenWindth;//计算宽度压缩比
        int heightScale = imgHeight/screenHeight;//计算高度压缩比
        int scale = 0;//最终的压缩比
        if(widthScale >= heightScale && widthScale>1){
            scale = widthScale;
        }else if(heightScale > widthScale && heightScale >1){
            scale = heightScale;
        }
        //4.压缩图片，得到一个新的图片
        //如果scale不等于0 说明一计算了一个压缩比用来压缩图片
        if(scale != 0){
            //设置压缩比给选项对象
            options.inSampleSize = scale;
            System.out.println("压缩比："+scale);
        }else{
            System.out.println("无需压缩");
        }
        //不管需不需要压缩，都要获取一个Bitmap所以需要将参数设置成false；
        options.inJustDecodeBounds = false;
        //将资源转换成Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat, options);
        //5.将图片加载给Imageview
        iv_dog.setImageBitmap(bitmap);
    }
}
