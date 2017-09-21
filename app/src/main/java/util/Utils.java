package util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static base.MyApplication.mContext;

public class Utils {

    private static final String TAG = "Utils";

    private static Toast mToast = null; // Toast

    private static long lastClickTime;

    /**
     * Toast
     *
     * @param context 上下文
     * @param msg     提示内容
     */
    public static void toast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * Toast
     *
     * @param resId 字串资源id
     */
    public static void toast(Context context, int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    private static Toast toast;
    public static void showToast(Context context,String text){
        if (toast==null){
            toast=Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 获取缓存目录
     *
     * @return 缓存目录
     */
    public static String getCacheDir(Context context) {
        Log.i(TAG, "CacheDir = " + context.getExternalCacheDir());
        return context.getExternalCacheDir() + Constant.CACHE_DIR;
    }

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory() + "/" + Constant.CACHE_DIR;
    }

    public static String getPictureStorageDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + Constant.CACHE_DIR;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context 上下文
     * @return 屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     *
     * @param
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context 上下文
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取缩放后的图片
     *
     * @param context 上下文
     * @param imgPath 图片路径
     * @return 缩放后的图片
     */
    public static Bitmap getScaledBitmap(Context context, String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 如果值设为true，那么将不返回实际的bitmap，也不给其分配内存空间，这样就避免了内存溢出。
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        if (bitmap == null) {
            // 这里Log可以打出来，是因为options.inJustDecodeBounds = true;
            Log.i(TAG, "bitmap为空");
        }
        int realwidth = options.outWidth;
        int realheight = options.outHeight;
        Log.i(TAG, "图片真实高度" + realheight + "宽度" + realwidth);

        int screenHeight = Utils.getScreenHeight(context); // 屏幕高度
        int screenWidth = Utils.getScreenWidth(context); // 屏幕宽度

        int heightScale = realheight / screenHeight; // 高度缩放比例
        int widthScale = realwidth / screenWidth; // 宽度缩放比例

        int scale = 1;
        // 若宽高缩放比都大于1，说明图片的实际宽高大于屏幕宽高，需要缩放
        if (heightScale > 1 && widthScale > 1) {
            // 使用较大的缩放比
            scale = heightScale > widthScale ? heightScale : widthScale;
        }
        options.inSampleSize = scale;
        // options.inSampleSize = 100;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imgPath, options);

        Log.i("TAG", "图片缩略图高度" + bitmap.getHeight() + "宽度" + bitmap.getWidth());

        return bitmap;
    }

    /**
     * 将图片转为Base64编码的字串，该操作比较耗时，需要放到线程执行
     *
     * @param context 上下文
     * @param imgPath 图片路径
     * @return Base64编码的字串
     */
    public static String getBitmapStrBase64(Context context, String imgPath) {
        Log.i(TAG, "imgPath = " + imgPath);
        Bitmap bitmap = Utils.getScaledBitmap(context, imgPath);
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 将图片转为Base64编码的字串，图片之间用","分割；该操作比较耗时，需要放到线程执行
     *
     * @param context 上下文
     * @param list    图片列表
     * @return Base64编码的字串
     */
    public static String getBitmapStrBase64(Context context, List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(Utils.getBitmapStrBase64(context, list.get(i)));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 将dp转换为px
     *
     * @param context 上下文
     * @param dp      dip数值
     * @return 将dp转换为px
     */
    public static float convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * 将Drawable转换成图片放到URl路径
     *
     * @param dw  Drawable
     * @param url 保存图片的Url
     */
    public static void saveFile(Drawable dw, String url) {
        try {
            Bitmap bm = ((BitmapDrawable) dw).getBitmap();

            // 获得文件名字
            final String fileName = url.substring(url.lastIndexOf("/") + 1, url.length())
                    .toLowerCase(Locale.getDefault());
            File file = new File(Environment.getExternalStorageDirectory() + "/image/" + fileName);
            // 创建图片缓存文件夹
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
            if (sdCardExist) {
                File maiduo = new File(Environment.getExternalStorageDirectory().toString());
                File ad = new File(Environment.getExternalStorageDirectory() + "/image");
                // 如果文件夹不存在
                if (!maiduo.exists()) {
                    // 按照指定的路径创建文件夹
                    maiduo.mkdir();
                    // 如果文件夹不存在
                } else if (!ad.exists()) {
                    // 按照指定的路径创建文件夹
                    ad.mkdir();
                }
                // 检查图片是否存在
                file.createNewFile();
            }

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络是否可用
     *
     * @param context 上下文
     * @return 网络是否可用
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 收起输入法键盘
     *
     * @param context   Context
     * @param tokenView 该输入法绑定的View
     */
    public static void colseInputMethod(Context context, View tokenView) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tokenView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开输入法键盘
     *
     * @param context   Context
     * @param tokenView 该输入法绑定的View
     */
    public static void openInputMethod(Context context, View tokenView) {
        tokenView.setFocusable(true);
        tokenView.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(tokenView.getWindowToken(), 0, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 验证手机号是否合法
     *
     * @param phoneNumber 手机号
     * @return boolean true:合法;false:不合法
     */
    public static boolean isPhoneLegal(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 验证车牌号是否合法
     *
     * @param Number 车牌号
     * @return boolean true:合法;false:不合法
     */
    public static boolean isPlateNumber(String Number) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$");
        Matcher m = p.matcher(Number);
        return m.matches();
    }

    /**
     * 验证数值是否合法
     *
     * @param digital 数值
     * @return boolean true:合法;false:不合法
     */
    public static boolean isDigitalLegal(String digital) {
        Pattern p = Pattern.compile("^\\d+$");
        Matcher m = p.matcher(digital);
        return m.matches();
    }


    public static String stringFilter(String str) throws PatternSyntaxException {
        String patth = "\\w+";
        //        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; //要过滤掉的字符
        Pattern p = Pattern.compile(patth);
        Matcher m = p.matcher(str);

        return m.replaceAll("").trim();
    }

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 750) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClickCamera() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取应用版本号
     *
     * @param context 上下文
     * @return 应用版本号
     */
    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }


    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    public static String getOSVersion(Context context) {
        return Build.VERSION.RELEASE;
    }

    /***
     * 获取应用程序信息
     * @param context
     * @param type  0  versionCode 1 versionName,2 packageName
     * @return
     */
    public static String getAppInfoByType(Context context, int type) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            switch (type) {
                case 0:
                    return packInfo.versionCode + "";
                case 1:
                    return packInfo.versionName;
                case 2:
                    return packInfo.packageName;
            }

        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    /**
     * 修改状态栏颜色
     *
     * @param context
     * @param color
     */
    public static void modifyStatusBar(FragmentActivity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //            window.setStatusBarColor(color);
        }
    }

    /**
     * 生成百度静态图求url
     *
     * @param width     图片宽
     * @param height    图片高
     * @param longitude 经度
     * @return
     */
    public static String generateBaiduMap(int width, int height, double longitude, double latitude, int zoom, String markUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.BAIDU_URL);
        sb.append("?");
        sb.append("ak=" + Constant.BAIDU_MAP_AK);
        sb.append("&");
        sb.append("center=" + longitude + "," + latitude);
        sb.append("&");
        sb.append("width=" + width);
        sb.append("&");
        sb.append("height=" + height);
        sb.append("&");
        sb.append("zoom=" + zoom);
        sb.append("&");
        sb.append("markers=" + longitude + "," + latitude);
        sb.append("&");
        sb.append("scale=" + 2);
        sb.append("&");
        sb.append("markerStyles=-1," + markUrl);

        //double longitude; // 经度
        //public double latitude; // 纬度

        //http://api.map.baidu.com/staticimage/v2?
        // ak=zGl3Htct9xnON1qwKGz2lUW5&
        // center=%E5%A4%A9%E5%AE%89%E9%97%A8&
        // width=300&
        // height=200&
        // zoom=16&
        // markers=%E5%A4%A9%E5%AE%89%E9%97%A8&
        // markerStyles=-1,http://master.bidostar.com/images/location.png,-1,203,205
        return sb.toString();
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.bidostar.pinan.version.VersionUpdateService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String saveMyBitmap(String bitName, Bitmap mBitmap) {
        String bitPath = Utils.getPictureStorageDirectory() + "sign/";
        String filePath = bitPath + bitName + ".png";
        File f = new File(bitPath + bitName + ".png");
        try {
            File dirs = new File(bitPath);
            if (!f.exists()) {
                dirs.mkdirs();//新建文件夹
            }
            new File(filePath).createNewFile();//新建文件
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载本地图片
     * http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算gridview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight += listView.getVerticalSpacing();

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        //        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 车牌号后五位有IOio的话换成01
     *
     * @param licensePlate
     * @return 转换后的字符串
     */
    public static String replaceIO(String licensePlate) {
        boolean flag = true;
        String before = licensePlate.substring(0, 2);
        String after = licensePlate.substring(2);
        if (after.contains("I")) {
            licensePlate = before + after.replaceAll("I", "1");
            flag = true;
        }
        if (after.contains("i")) {
            licensePlate = before + after.replaceAll("i", "1");
            flag = true;
        }
        if (after.contains("O")) {
            if (flag) {
                licensePlate = licensePlate.replaceAll("O", "0");
            } else {
                licensePlate = before + after.replaceAll("O", "0");
            }
        }
        if (after.contains("o")) {
            if (flag) {
                licensePlate = licensePlate.replaceAll("o", "0");
            } else {
                licensePlate = before + after.replaceAll("o", "0");
            }
        }
        return licensePlate;
    }

    //获取文件夹下文件大小
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    public static String getSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * 计算两坐标间的距离，返回米
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = 180 / 3.14169d;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        return 6366000 * tt;
    }

    /**
     * 计算两坐标间的距离，返回米
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static String getDistance1(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = 180 / 3.14169d;
        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;
        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        int v = (int) (6366000 * tt);
        int km = v / 1000;

        StringBuilder sb = new StringBuilder();
        if (km > 0) {
            int m = v % 1000 / 100;
            Log.d(TAG, "m:" + m);
            sb.append(km + "." + m + "km");
        } else {
            sb.append(v + "m");
        }

        return sb.toString();
    }

    /**
     * 获取图片的创建时间
     * @param filePath 照片的路径
     * @return file的lastModifyTime,Linux 系统没有createTime。
     */
    public static String getPictureCreateTime(String filePath) {
        if (Constant.DEBUG)
            Log.e(TAG, "getPictureCreateTime() called with: filePath = [" + filePath + "]");
        String time = "";
        // : 2017/4/18 等接口类型，完善功能
        File file = new File(filePath);
        if (file != null && file.exists()) {
            long tiem = file.lastModified();
            time = DateFormatUtils.format(new Date(tiem),DateFormatUtils.PATTERN_FULL);
        }
        return time;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取wifi连接状态
     * @param context 上下文
     * @return true 是WiFi连接，false 不是WiFi网络
     */
    public static boolean getWIFIConnectStatus(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}