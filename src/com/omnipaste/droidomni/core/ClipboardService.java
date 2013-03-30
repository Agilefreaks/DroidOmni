package com.omnipaste.droidomni.core;

import com.droidomni.R;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ClipboardService extends Service {

	public ClipboardService() {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		keepAlive();
	}

	void keepAlive() {
		Notification notification = new Notification.Builder(this)
				.setSmallIcon(R.drawable.arrow)
				.setContentTitle(getText(R.string.app_name))
				.setContentText(getText(R.string.notification_message))
				.setWhen(System.currentTimeMillis())
				.setOngoing(true)
				.build();

		startForeground(R.id.action_settings, notification);
	}
}
