package ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bean.VirusInfo;
import db.VirusDAO;
import util.MD5Utils;
import videodemo.R;

/**
 * Created by admin on 2017-07-20.
 */

public class MobileVirusScanningActivity extends Activity {

    private LinearLayout llResult;
    private TextView tvResult;
    private Button btnScan;
//    private ArcProgress mProg;
    private TextView tvPackageName;
    private ListView lvAA;
    private PackageManager mPm;
    private ArrayList<VirusInfo> mInfos;
    private LinearLayout llProg;
    private LinearLayout llAnim;
    private ImageView ivLeft;
    private ImageView ivRight;
    private VirusTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antivirus);
        mPm = getPackageManager();
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 结果
        llResult = (LinearLayout) findViewById(R.id.ll_aa_result);
        tvResult = (TextView) findViewById(R.id.tv_aa_result);
        btnScan = (Button) findViewById(R.id.btn_aa_scan);
        // 过程
        llProg = (LinearLayout) findViewById(R.id.ll_aa_prog);
//        mProg = (ArcProgress) findViewById(R.id.arc_progress);
        tvPackageName = (TextView) findViewById(R.id.tv_aa_packagename);

        // 动画布局
        llAnim = (LinearLayout) findViewById(R.id.ll_aa_anim);
        ivLeft = (ImageView) findViewById(R.id.iv_aa_left);
        ivRight = (ImageView) findViewById(R.id.iv_aa_right);

        lvAA = (ListView) findViewById(R.id.lv_aa);

    }

    private void initData() {
        // 执行扫描
        scan();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 配置 android:configChanges="keyboardHidden|orientation|screenSize"
        // 切屏时走这里
    }

    private class VirusTask extends AsyncTask<Void, VirusInfo, Void> {

        private VirusAdapter mAdapter;
        private int max;// 进度的最大值
        private int progress;// 声明进度
        private boolean isStop;// 是否停止任务
        private int virusCount;

        // 异步子线程
        @Override
        protected Void doInBackground(Void... params) {
            // 获取应用信息数据 获取应用的签名信息 要添加flag

            List<PackageInfo> installedPackages = mPm
                    .getInstalledPackages(PackageManager.GET_SIGNATURES);
            // 获取进度的最大值
            max = installedPackages.size();

            for (PackageInfo packageInfo : installedPackages) {
                if (isStop) {
                    // 停止循环
                    break;
                }
                // 包名
                String packageName = packageInfo.packageName;
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                // 图标
                Drawable icon = applicationInfo.loadIcon(mPm);
                // 名称
                String name = applicationInfo.loadLabel(mPm).toString();

                // 获取应用的签名信息
                Signature[] signatures = packageInfo.signatures;
                // 给签名信息 md5加密
                String md5 = MD5Utils.enCode(signatures[0].toCharsString());

                // 检测是否有毒
                boolean virus = VirusDAO.isVirus(getApplicationContext(), md5);

                VirusInfo info = new VirusInfo(packageName, icon, name, virus);

                progress++;
                // 触发进度更新onProgressUpdate方法执行
                publishProgress(info);
                SystemClock.sleep(100);
            }

            return null;
        }

        /**
         * 停止任务
         */
        public void stop() {
            isStop = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 预加载
            mInfos = new ArrayList<VirusInfo>();
            mAdapter = new VirusAdapter();
            // 更新listview界面 此时 数据大小为0
            lvAA.setAdapter(mAdapter);

            // 隐藏结果布局
            llResult.setVisibility(View.INVISIBLE);
            // 显示过程布局
            llProg.setVisibility(View.VISIBLE);
            // 隐藏动画的布局
            llAnim.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (isStop) {
                // 停止任务
                return;
            }
            // 任务结束 更新UI
            // 扫描完 滚回第0条
            lvAA.smoothScrollToPosition(0);

            // 显示结果布局
            llResult.setVisibility(View.VISIBLE);
            // 隐藏过程布局
            llProg.setVisibility(View.INVISIBLE);
            // 显示动画的布局
            llAnim.setVisibility(View.VISIBLE);

            // 1.获取过程布局的缓存图片
            llProg.setDrawingCacheEnabled(true);// 开启图片缓存
            llProg.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);// 设置图片质量
            Bitmap drawingCache = llProg.getDrawingCache();// 获取缓存图片
            // 2.获取左边的图片
            Bitmap leftBitmap = getLeftBitmap(drawingCache);
            // 3.获取右边的图片
            Bitmap rightBitmap = getRightBitmap(drawingCache);

            // 4.设置动画view的图片
            ivLeft.setImageBitmap(leftBitmap);
            ivRight.setImageBitmap(rightBitmap);

            // 5.开启开门动画
            startOpenAnim();

            // 病毒数量
            if (virusCount > 0) {
                tvResult.setText("您的手机有病毒");
            }
        }

        @Override
        protected void onProgressUpdate(VirusInfo... values) {
            super.onProgressUpdate(values);
            if (isStop) {
                // 停止任务
                return;
            }

            // 获取传进来的数据
            VirusInfo info = values[0];
            // 添加到数据源
            if (info.isVirus) {
                // 有毒 添加到数据源的第0个
                mInfos.add(0, info);
                // 病毒数量增加
                virusCount++;
            } else {
                mInfos.add(info);
            }

            // 进度更新
            mAdapter.notifyDataSetChanged();

            // 滚动到指定的索引位置
            lvAA.smoothScrollToPosition(mInfos.size() - 1);

            // 更新progress的进度
//            mProg.setProgress((int) (progress * 100.0f / max + 0.5f));
            // 显示扫描的应用的包名
            tvPackageName.setText(info.packageName);
        }

    }

    private class VirusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public VirusInfo getItem(int position) {
            return mInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_virus, null);
                viewHolder.ivIcon = (ImageView) convertView
                        .findViewById(R.id.iv_iv_icon);
                viewHolder.tvName = (TextView) convertView
                        .findViewById(R.id.tv_iv_name);
                viewHolder.tvSafe = (TextView) convertView
                        .findViewById(R.id.tv_iv_safe);
                viewHolder.ivClean = (ImageView) convertView
                        .findViewById(R.id.iv_iv_clean);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            VirusInfo info = getItem(position);
            viewHolder.ivIcon.setImageDrawable(info.icon);
            viewHolder.tvName.setText(info.name);
            viewHolder.tvSafe.setText(info.isVirus ? "病毒" : "安全");
            viewHolder.tvSafe.setTextColor(info.isVirus ? Color.RED
                    : getResources().getColor(R.color.virus_green));
            viewHolder.ivClean.setVisibility(info.isVirus ? View.VISIBLE
                    : View.INVISIBLE);

            return convertView;
        }

    }

    static class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvSafe;
        ImageView ivClean;
    }

    /**
     * 重新扫描的点击事件
     *
     * @param v
     */
    public void reScan(View v) {
        // 关门动画
        startCloseAnim();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出页面的时候 停止扫描
        stopScan();
    }

    /**
     * 停止 扫描
     */
    private void stopScan() {
        // mTask.cancel(mayInterruptIfRunning) cancel方法停不了后台任务
        if (mTask != null) {
            mTask.stop();
            mTask = null;
        }
    }

    /**
     * 扫描
     */
    private void scan() {
        // 每次扫描之前 停止上一次的任务
        stopScan();
        // AsyncTask的execute方法 只允许执行一次 所以这里再生成一个新的对象
        mTask = new VirusTask();
        // 执行扫描任务
        mTask.execute();

    }

    /**
     * 获取左边图片
     *
     * @param drawingCache
     * @return
     */
    public Bitmap getLeftBitmap(Bitmap drawingCache) {
        // 宽度是原图一半
        int width = drawingCache.getWidth() / 2;
        int height = drawingCache.getHeight();
        // 生成目标图片
        Bitmap destBitmap = Bitmap.createBitmap(width, height,
                drawingCache.getConfig());
        // 创建一个画布
        Canvas canvas = new Canvas(destBitmap);

        // 矩阵 可以移动图片
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        // 把原图画到画布上
        canvas.drawBitmap(drawingCache, matrix, paint);

        return destBitmap;
    }

    /**
     * 开启开门动画
     */
    public void startOpenAnim() {

        // 动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        // ivLeft.setTranslationX(translationX)
        // ivLeft.setAlpha(alpha)

        // 把动画放到集合里
        animatorSet.playTogether(
                // 左边图片 向左移动动画
                ObjectAnimator.ofFloat(ivLeft, "translationX", 0,
                        -ivLeft.getWidth()),
                // 左边图片 渐变消失动画
                ObjectAnimator.ofFloat(ivLeft, "alpha", 1.0f, 0.0f),
                // 右边图片 向右移动动画
                ObjectAnimator.ofFloat(ivRight, "translationX", 0,
                        ivRight.getWidth()),
                // 右边图片 渐变消失动画
                ObjectAnimator.ofFloat(ivRight, "alpha", 1.0f, 0.0f),
                // 结果页面 渐变显示动画
                ObjectAnimator.ofFloat(llResult, "alpha", 0.0f, 1.0f));

        // 设置动画时间
        animatorSet.setDuration(2500);
        // 监听动画 //动画结束的时候 可用 重新扫描
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束的时候 可用 重新扫描
                btnScan.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

        // 开启动画
        animatorSet.start();
    }

    /**
     * 开启关门动画
     */
    public void startCloseAnim() {

        // 动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        // ivLeft.setTranslationX(translationX)
        // ivLeft.setAlpha(alpha)

        // 把动画放到集合里
        animatorSet.playTogether(
                // 左边图片 向右移动动画
                ObjectAnimator.ofFloat(ivLeft, "translationX",
                        -ivLeft.getWidth(), 0),
                // 左边图片 渐变显示动画
                ObjectAnimator.ofFloat(ivLeft, "alpha", 0.0f, 1.0f),
                // 右边图片 向左移动动画
                ObjectAnimator.ofFloat(ivRight, "translationX",
                        ivRight.getWidth(), 0),
                // 右边图片 渐变显示动画
                ObjectAnimator.ofFloat(ivRight, "alpha", 0.0f, 1.0f),
                // 结果页面 渐变消失动画
                ObjectAnimator.ofFloat(llResult, "alpha", 1.0f, 0.0f));

        // 设置动画时间
        animatorSet.setDuration(2500);

        // 添加动画监听 动画结束重新扫描
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // 关门动画开始后 不可用
                btnScan.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束重新扫描
                scan();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        // 开启动画
        animatorSet.start();
    }

    /**
     * 获取右边图片
     *
     * @param drawingCache
     * @return
     */
    public Bitmap getRightBitmap(Bitmap drawingCache) {
        // 宽度是原图一半
        int width = drawingCache.getWidth() / 2;
        int height = drawingCache.getHeight();
        // 生成目标图片
        Bitmap destBitmap = Bitmap.createBitmap(width, height,
                drawingCache.getConfig());
        // 创建一个画布
        Canvas canvas = new Canvas(destBitmap);

        // 矩阵 可以移动图片
        Matrix matrix = new Matrix();
        // 把原图往左移动一半的宽度
        matrix.setTranslate(-width, 0);
        // 画笔
        Paint paint = new Paint();
        // 把原图画到画布上
        canvas.drawBitmap(drawingCache, matrix, paint);

        return destBitmap;
    }

}
