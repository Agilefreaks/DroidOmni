package com.omnipasteapp.omnipaste.backgroundServices;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.enums.Sender;
import com.omnipasteapp.omnipaste.services.IntentService;

@EService
public class OmnipasteService extends Service implements ICanReceiveData {
  public static final String EXTRA_STARTED = "started";
  public static final String EXTRA_CLIPBOARD_SENDER = "clipboardSender";
  public static final String EXTRA_CLIPBOARD_DATA = "clipboardData";

  public IOmniService omniService;

  @SystemService
  public NotificationManager notificationManager;

  @StringRes
  public String omnipasteServiceStatusChanged;

  @StringRes
  public String omnipasteDataReceived;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public synchronized int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    if (omniService != null) {
      stopSelf();
    }

    omniService = OmnipasteApplication.get(IOmniService.class);

    try {
      omniService.start();
      omniService.addListener(this);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    notifyStarted();

    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    omniService.stop();
    omniService = null;

    notifyStopped();

    super.onDestroy();
  }

  public void notifyStarted() {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_STARTED, true);

    IntentService.sendBroadcast(this, omnipasteServiceStatusChanged, intent);
  }

  public void notifyStopped() {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_STARTED, false);

    IntentService.sendBroadcast(this, omnipasteServiceStatusChanged, intent);
  }

  //region ICanReceiveData
  @Override
  public void dataReceived(IClipboardData clipboardData) {
    Intent intent = new Intent();

    Sender sender;

    if (clipboardData.getSender() instanceof ILocalClipboard) {
      sender = Sender.Local;
    }
    else {
      sender = Sender.Omni;
    }

    intent.putExtra(EXTRA_CLIPBOARD_SENDER, sender);
    intent.putExtra(EXTRA_CLIPBOARD_DATA, clipboardData.getData());

    IntentService.sendBroadcast(this, omnipasteDataReceived, intent);
  }
  //endregion
}
