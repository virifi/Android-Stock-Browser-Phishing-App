package net.virifi.android.phishingapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int GREEN = 0;
	private static final int RED = 1;
	
	private Button mServiceButton = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mServiceButton = (Button) findViewById(R.id.button1);
        mServiceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent serviceIntent = new Intent(MainActivity.this, HistoryObserverService.class);
				// サービスが実行中の場合はサービスを停止
				if (HistoryObserverService.isRunning) {
					stopService(serviceIntent);
					setButtonColor(GREEN);
				} else {
					// 実行中でない場合サービスを起動させる
					startService(serviceIntent);
					setButtonColor(RED);
				}
			}
		});
        
        Button mitsuiButton = (Button) findViewById(R.id.button2);
        mitsuiButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.smbc.co.jp/smartphone/index.html"));
				browserIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				try {
					startActivity(browserIntent);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(), "標準ブラウザが見つかりませんでした", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

    @Override
	protected void onResume() {
		super.onResume();
		if (HistoryObserverService.isRunning)
			setButtonColor(RED);
		else
			setButtonColor(GREEN);
	}
	
	private void setButtonColor(int color) {
		if (color == RED) {
			mServiceButton.setBackgroundResource(R.drawable.red_stateful);
			mServiceButton.setTextColor(Color.parseColor("#FF303030"));
			mServiceButton.setText("標準ブラウザの監視を停止");
		} else if (color == GREEN){
			mServiceButton.setBackgroundResource(R.drawable.green_stateful);
			mServiceButton.setTextColor(Color.parseColor("#FF46483E"));
			mServiceButton.setText("標準ブラウザの監視を開始");
		}		
	}
}
