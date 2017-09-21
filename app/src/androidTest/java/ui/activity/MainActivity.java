package ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import videodemo.R;


/**
 * Created by zhush on 2016/11/11

 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 10);
//            } else {
//                AddrToast at = new AddrToast(getApplicationContext());
//                at.show();
//            }
//        }


        findViewById(R.id.btn_shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), UpdataRestarActivity.class));

            }
        });

        findViewById(R.id.button4).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(), DaShenActivity.class));
                return false;
            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 10) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (!Settings.canDrawOverlays(this)) {
//                    Toast.makeText(getApplication(), "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
//                } else {
////                    Toast.makeText(TestFloatWinActivity.this, "权限授予成功！", Toast.LENGTH_SHORT).show();
//                    // SYSTEM_ALERT_WINDOW permission not granted...
//                    AddrToast at = new AddrToast(getApplicationContext());
//                    at.show();
//                }
//
//            }
//        }
//    }
}
