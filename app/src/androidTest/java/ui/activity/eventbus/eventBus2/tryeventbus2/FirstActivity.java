package ui.activity.eventbus.eventBus2.tryeventbus2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ui.activity.eventbus.eventBus2.other.FirstEvent;
import ui.activity.eventbus.eventBus2.other.SecondEvent;
import ui.activity.eventbus.eventBus2.other.ThirdEvent;
import videodemo.R;

public class FirstActivity extends Activity {

	Button btn;
	TextView tv;
	EventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);

		EventBus.getDefault().register(this);

		btn = (Button) findViewById(R.id.btn_try);

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						SecondActivity.class);
				startActivity(intent);
			}
		});
	}
	@Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
	public void onEventMainThread(FirstEvent event) {
		Toast.makeText(this, event.getMsg()+"", Toast.LENGTH_SHORT).show();


	}

	@Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
	public void onEventMainThread(SecondEvent event) {

		Toast.makeText(this, event.getMsg()+"", Toast.LENGTH_SHORT).show();
	}
	@Subscribe(threadMode = ThreadMode.BACKGROUND) //在ui线程执行//SecondEvent���պ�����
	public void onEventBackgroundThread(SecondEvent event){
		Toast.makeText(this, event.getMsg()+"", Toast.LENGTH_SHORT).show();
	}
	@Subscribe(threadMode = ThreadMode.ASYNC)//SecondEvent���պ�����
	public void onEventAsync(SecondEvent event){
		Toast.makeText(this, event.getMsg()+"", Toast.LENGTH_SHORT).show();
	}
	@Subscribe()
	public void onEvent(ThirdEvent event) {
		Toast.makeText(this, event.getMsg()+"", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
