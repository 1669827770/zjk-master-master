package ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import videodemo.R;

/**
 * Created by admin on 2017-04-21.
 */

public class WeigetMoveActivity extends Activity implements View.OnTouchListener {
    ImageView _view,_view2;
    ViewGroup _root;
    private int lastX, lastY;
//    final static int IMAGE_SIZE = 72;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_weiget);


        _view = (ImageView) findViewById(R.id.id_text);
        _view2 = (ImageView) findViewById(R.id.id_text2);
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                IMAGE_SIZE, IMAGE_SIZE);
//        layoutParams.leftMargin = IMAGE_SIZE;
//        layoutParams.topMargin = IMAGE_SIZE;
//
//        _view.setLayoutParams(layoutParams);
        _view.setOnTouchListener(this);
//        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
//                IMAGE_SIZE, IMAGE_SIZE);
//        layoutParams2.leftMargin = 3*IMAGE_SIZE;
//        layoutParams2.topMargin = IMAGE_SIZE;
//
//        _view2.setLayoutParams(layoutParams2);
//        _view2.setOnTouchListener(this);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) _view
//                        .getLayoutParams();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = _view.getLeft() + dx;
                int top = _view.getTop() + dy;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) _view
                        .getLayoutParams();
//                layoutParams.height=IMAGE_SIZE;
//                layoutParams.width = IMAGE_SIZE;
                layoutParams.leftMargin =left;
                layoutParams.topMargin =top;
                view.setLayoutParams(layoutParams);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
        }
        _view.invalidate();
        return true;
    }
}