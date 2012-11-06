package net.virifi.android.phishingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PhishingActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phishing_activity);
		
		/*
		Bundle extras = getIntent().getExtras();
		if (extras == null) return;
		String url = extras.getString("url");
		if (url == null) return;
		*/
		
		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.setWebViewClient(new WebViewClient() {});
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new Object() {
			public void send(String str) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.co.jp/search?q=" + str));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		}, "js_interface");
		//webView.loadUrl(url);
		webView.loadUrl("file:///android_asset/phishing.html");
		
		Toast.makeText(this, "フィッシングアプリで表示しています", Toast.LENGTH_LONG).show();
	}
}
