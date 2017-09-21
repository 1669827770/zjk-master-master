package ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import videodemo.R;

/**
 * Created by admin on 2017-07-18.
 */

public class TearClothesActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tearclothes);

        final ImageView iv_pre = (ImageView) findViewById(R.id.iv_pre);
        //1.加载一个穿衣服的图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pre19);
        //2.创建原图的副本
        final Bitmap bitmap_copy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap_copy);
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        //3.为iv加载副本
        iv_pre.setImageBitmap(bitmap_copy);
        //4.设置iv的触摸事件
        iv_pre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当控件被触摸时执行
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        try{
                            //5.获取当前移动到的位置的坐标点
                            int x = (int) event.getX();
                            int y = (int) event.getY();
                            //6.设置当前坐标点为透明
                            for(int j =0 ;j < 20;j++){
                                for(int i =0 ;i < 20;i++){
                                    bitmap_copy.setPixel(x+i, y+j, Color.TRANSPARENT);
                                }
                            }
                            //7.为iv设置新的bitmap
                            iv_pre.setImageBitmap(bitmap_copy);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }




}
