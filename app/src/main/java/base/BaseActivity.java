package base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import services.Service1;
import videodemo.R;


public abstract class BaseActivity extends FragmentActivity {

    private Dialog mDialog; // 自定义的Dialog
    private Dialog mNotifyDialog;//dialog2

    private boolean mIsVisiable;
    InputMethodManager manager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MyApplication) getApplication()).addActivity(this);
        mDialog = new Dialog(this, R.style.SettleDialog);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.custom_loading_dialog2);
        mNotifyDialog = new Dialog(this, R.style.SettleDialog1);
        mNotifyDialog.setCancelable(false);
        mNotifyDialog.setContentView(R.layout.custom_dialog_layout3);

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIsVisiable = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsVisiable = false;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication) getApplication()).removeActivity(this);
    }

    /**
     * 沉浸状态栏
     *
     * @param flag 状态栏是否沉浸
     */
    @TargetApi(value = 19)
    protected void immerseStatusBar(boolean flag) {
        if (flag) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 显示Dialog
     *
     * @param content Dialog中要显示的内容
     */
    public void showCustomDialog(String content) {
        /*TextView tv = (TextView) mDialog.findViewById(R.id.message);
        tv.setText(content);*/
        mDialog.show();
    }

    /**
     * 显示Dialog
     *
     * @param resid Dialog中要显示的内容
     * @return
     */
    public Dialog showCustomDialog(int resid) {
        /*TextView tv = (TextView) mDialog.findViewById(R.id.message);
        tv.setText(resid);*/
        mDialog.show();
        return mDialog;
    }

    /**
     * 取消Dialog
     */
    public void dismissCustomDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * Toast
     *
     * @param msg
     * 提示内容
     */
    private static Toast mToast = null;

    public void toast(String msg) {
        if (this != null) {
            if (mToast == null) {
                mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(msg);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }

            mToast.show();
        }
    }

    /**
     * Toast
     *
     * @param resId 字串资源id
     */
    public void toast(int resId) {
        if (this != null) {
            if (mToast == null) {
                mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }

            mToast.show();
        }
    }

    public boolean isVisiable() {
        return mIsVisiable;
    }

    /**
     * 启动页 右滑进 关闭页 左滑出
     */
    public void slideFromRight() {
        overridePendingTransition(R.anim.activity_slide_right_in, R.anim.activity_slide_left_out);
    }

    /**
     * 启动页 左进 关闭页 右出
     */
    public void slideFromLeft() {
        overridePendingTransition(R.anim.activity_slide_left_in, R.anim.activity_slide_right_out);
    }

    /**
     * 底部滑入
     */
    public void slideBottomIn() {
        overridePendingTransition(R.anim.activity_slide_bottom_in, R.anim.activity_alpha_out);
    }

    /**
     * 底部滑出
     */
    public void slideBottomOut() {
        overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_slide_bottom_out);
    }

    /**
     * 放大进入
     */
    public void scaleZoomIn() {
        overridePendingTransition(R.anim.activity_scale_zoom_in, R.anim.activity_alpha_out);
    }

    /**
     * 缩小退出
     */
    public void scaleZoomOut() {
        overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_scale_zoom_out);
    }

    /**
     * 显示错误提示Dialog
     * <p>
     * Dialog中要显示的内容
     */
    public void showCustomNoticeDialog(String content) {
        TextView tv = (TextView) mNotifyDialog.findViewById(R.id.tv_content);
        tv.setText(content);
        mNotifyDialog.show();
    }

    public void showCustomNoticeDialogCanCancle(String content) {
        TextView tv = (TextView) mNotifyDialog.findViewById(R.id.tv_content);
        tv.setText(content);
        mNotifyDialog.setCancelable(true);
        mNotifyDialog.show();
    }

    public void showCustomNoticeDialog(int content) {
        TextView tv = (TextView) mNotifyDialog.findViewById(R.id.tv_content);
        tv.setText(content);
        mNotifyDialog.show();
    }

    /**
     * 取消Dialog
     */
    public void dismissCustomNoticeDialog() {
        if (mNotifyDialog != null) {
            mNotifyDialog.dismiss();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Bitmap 转换成Uri
     *
     * @param bitmap
     * @return Uri
     */
    public Uri bitmapToUri(Bitmap bitmap) {
        return Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
    }

    /**
     * 转换价格
     *
     * @param textView
     */
    public void convertPrice(TextView textView) {
        int start = 0;
        int center = 1;
        int end = textView.getText().toString().length();
        String showPlay = textView.getText().toString();
        if (showPlay.startsWith("-")) {
            center++;
        }
        Spannable span = new SpannableString(textView.getText());
        span.setSpan(new AbsoluteSizeSpan(10, true), start, center, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(17, true), center, end - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(10, true), end - 3, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    public void startService() {
        Log.e("mush", "startService");
        Intent intent = new Intent(this, Service1.class);
        startService(intent);
    }

    public void stopService() {
        Log.e("mush", "stopService");
        Intent intent = new Intent(this, Service1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        stopService(intent);
    }
}
