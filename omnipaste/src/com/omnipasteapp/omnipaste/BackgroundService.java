package com.omnipasteapp.omnipaste;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.framework.IOmniServiceFactory;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnipaste.enums.BackgroundServiceStates;
import roboguice.service.RoboService;

public class BackgroundService extends RoboService {

  public static BackgroundServiceStates serviceState = BackgroundServiceStates.Inactive;

  private IOmniService _omniService;

  @Inject
  private IOmniServiceFactory _omniServiceFactory;

  @Inject
  private NotificationManager _notificationManager;

  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    start();
    keepAlive();

    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    stop();

    super.onDestroy();
  }

  public synchronized void start() {

    _omniService = _omniServiceFactory.create();

    try {
      _omniService.start();
      serviceState = BackgroundServiceStates.Active;
    } catch (InterruptedException e) {
      e.printStackTrace(); // handle this in a smarter way
    }
  }

  public synchronized void stop() {
    _omniService.stop();
    _omniService = null;
    _notificationManager.cancel(R.id.action_settings);
    serviceState = BackgroundServiceStates.Inactive;
  }

  public void keepAlive() {
    Notification notification = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.notification_isSyncing_message))
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)
            .setContentIntent(PendingIntent.getActivity(this, 0,
                    new Intent(this, MainActivity.class),0))
            .getNotification();

    _notificationManager.notify(R.id.action_settings, notification);
  }
}
