package com.omnipasteapp.omnipaste.backgroundServices;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.SystemService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.OmnipasteApplication;

@EService
public class OmnipasteService extends Service {
  public IOmniService omniService;

  @SystemService
  public NotificationManager notificationManager;

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
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    omniService.stop();
    omniService = null;

    super.onDestroy();
  }
}
