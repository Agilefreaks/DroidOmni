package com.omnipasteapp.omni.core;

import android.app.Notification;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import com.omnipasteapp.omni.R;
import com.omnipasteapp.omni.core.communication.Clipboard;
import com.omnipasteapp.omni.core.communication.ClipboardMediator;
import com.omnipasteapp.omni.core.communication.DefaultClipboardMediator;
import com.omnipasteapp.omni.core.communication.impl.cloud.pubnub.PubNubService;
import com.omnipasteapp.omni.core.communication.impl.local.SystemClipboard;

public class ClipboardService extends Service {

    private ClipboardMediator _defaultClipboardMediator;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

        init();
		keepAlive();
	}

    private void init(){
        Clipboard cloudClipboard = new PubNubService("channel_name");
        Clipboard localClipboard = new SystemClipboard((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));

        _defaultClipboardMediator = new DefaultClipboardMediator();
        _defaultClipboardMediator.setCloudClipboard(cloudClipboard);
        _defaultClipboardMediator.setLocalClipboard(localClipboard);
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
