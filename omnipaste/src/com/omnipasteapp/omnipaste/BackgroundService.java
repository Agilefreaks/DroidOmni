package com.omnipasteapp.omnipaste;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import roboguice.service.RoboService;

public class BackgroundService extends RoboService {

  @Inject
  private IOmniService omniService;

  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    init();
    keepAlive();

    return START_STICKY;
  }

  public void init(){
    try {
      omniService.start();
    } catch (InterruptedException e) {
      e.printStackTrace(); // handle this in a smarter way
    }
  }

  public void keepAlive(){
    Notification notification = new Notification.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.notification_isSyncing_message))
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)
            .build();

    startForeground(R.id.action_settings, notification);
  }
}