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
import com.omnipasteapp.omni.core.communication.RemoteClipboard;
import com.omnipasteapp.omni.core.communication.impl.cloud.pubnub.PubNubService;
import com.omnipasteapp.omni.core.communication.impl.local.SystemClipboard;

public class ClipboardService extends Service {

    public static final String TAG = "ClipboardService";
    public static final String CHANNEL_NAME = "CHANNEL";

    public static final String START = "START";
    public static final String STOP = "STOP";

    private Clipboard _localClipboard;
    private RemoteClipboard _remoteClipboard;
    private ClipboardMediator _clipboardMediator;

    @Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        init(intent.getStringExtra(CHANNEL_NAME));
        keepAlive();

        return START_STICKY;
    }

    private void init(String channelName){
        _remoteClipboard = new PubNubService(channelName);
        _localClipboard = new SystemClipboard((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));

        _clipboardMediator = new DefaultClipboardMediator();
        _clipboardMediator.setLocalClipboard(_localClipboard);
        _clipboardMediator.setRemoteClipboard(_remoteClipboard);
    }

    private void keepAlive() {
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.arrow)
                .setContentTitle(getText(R.string.app_name))
                .setContentText(getText(R.string.notification_message))
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .build();

        startForeground(R.id.action_settings, notification);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        _clipboardMediator.setLocalClipboard(null);
        _clipboardMediator.setRemoteClipboard(null);
        _remoteClipboard.disconnect();
    }
}
