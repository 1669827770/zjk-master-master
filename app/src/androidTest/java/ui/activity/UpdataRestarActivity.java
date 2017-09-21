package ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by admin on 2017-07-19.
 */

public class UpdataRestarActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        ProcessBuilder pb = new ProcessBuilder("ls", "-l");
//        pb.directory(new File("/dev"));  //切换到工作目录 /dev
//        Map<String, String> map = pb.environment();  // 得到进程生成器的环境 变量,这个变量可以自己修改
//        try {
//            Process p = pb.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.e("3333333333333333", "66666666666666664 " );

        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


//        Intent intent2 = new Intent(Intent.ACTION_REBOOT);
//        intent2.putExtra("nowait", 1);
//        intent2.putExtra("interval", 1);
//        intent2.putExtra("window", 0);
//        sendBroadcast(intent2);


//        Intent intent2 = new Intent(Intent.ACTION_REBOOT);
//        intent2.putExtra("nowait", 1);
//        intent2.putExtra("interval", 1);
//        intent2.putExtra("window", 0);
//        sendBroadcast(intent2);

//        PowerManager pManager=(PowerManager) getSystemService(Context.POWER_SERVICE);  //重启到fastboot模式
//        pManager.reboot("");

//        SystemProperties.set("ctl.start", "system_shutdwon");

//        SystemProperties.set("ctl.start", "system_reboot");

//        Process proc = null; //关机
//        try {
//            proc =Runtime.getRuntime().exec(new String[]{"su","-c","shutdown"});
////            proc = Runtime.getRuntime().exec(new String[]{"su","-c","reboot -p"});
//            proc.waitFor();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, true);
//        //其中false换成true,会弹出是否关机的确认窗口
//        startActivity(intent);



//            //获得ServiceManager类
//        Class<?> ServiceManager = null;
//        try {
//            ServiceManager = Class.forName("android.os.ServiceManager");
//        //获得ServiceManager的getService方法
//            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
//            //调用getService获取RemoteService
//            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
//            //获得IPowerManager.Stub类
//            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
//            //获得asInterface方法
//            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
//            //调用asInterface方法获取IPowerManager对象
//            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
//            //获得shutdown()方法
//            Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
//            //调用shutdown()方法
//            shutdown.invoke(oIPowerManager,false,true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String[] args = {"shutdown"};
//        exec(args);



    }

//    /** 执行Linux命令，并返回执行结果。 */
//    public static String exec(String[] args) {
//        String result = "";
//        ProcessBuilder processBuilder = new ProcessBuilder(args);
//        Process process = null;
//        InputStream errIs = null;
//        InputStream inIs = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int read = -1;
//            process = processBuilder.start();
//            errIs = process.getErrorStream();
//            while ((read = errIs.read()) != -1) {
//                baos.write(read);
//            }
//            baos.write('\n');
//            inIs = process.getInputStream();
//            while ((read = inIs.read()) != -1) {
//                baos.write(read);
//            }
//            byte[] data = baos.toByteArray();
//            result = new String(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (errIs != null) {
//                    errIs.close();
//                }
//                if (inIs != null) {
//                    inIs.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (process != null) {
//                process.destroy();
//            }
//        }
//        return result;
//    }

}
