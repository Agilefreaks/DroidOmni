package com.omnipaste.droidomni.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.events.GcmEvent;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.res.StringRes;

import de.greenrobot.event.EventBus;

@EService
public class OmniService extends Service {
  private EventBus eventBus = EventBus.getDefault();
  private Boolean started = false;

  @StringRes
  public String appName;

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);

    start();

    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    // cancel notification
    eventBus.unregister(this);
    stop();
  }

  @SuppressWarnings("UnusedDeclaration")
  public void onEventBackgroundThread(GcmEvent gcmEvent) {
    Log.i("dude", "event");
  }

  private void start() {
    if (!started) {
      eventBus.register(this);
      notifyUser();

      started = true;
    }
  }

  private void notifyUser() {
    final int serviceId = 42;

    Intent resultIntent = new Intent(this, MainActivity_.class);
    resultIntent.setAction("android.intent.action.MAIN");
    resultIntent.addCategory("android.intent.category.LAUNCHER");

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(appName)
        .setContentText("")
        .setOngoing(true)
        .setContentIntent(contentIntent);

    startForeground(serviceId, builder.build());
  }

  private void stop() {
    if (started) {
      started = false;
      stopForeground(true);
    }
  }
}
