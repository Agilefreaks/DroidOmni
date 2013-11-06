package com.omnipasteapp.omnipaste.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.activities.MainActivity_;

@EService
public class NotificationService extends IntentService {

  public static final int NOTIFICATION_ID = 42;

  public static final String ACTION = "com.omnipasteapp.omnipaste.services.ACTION";
  public static final String TEXT = "com.omnipasteapp.omnipaste.services.TEXT";

  public enum ActionType {
    Create,
    Destroy,
    Update
  }

  @StringRes
  public String appName;

  @SystemService
  public NotificationManager notificationManager;

  public NotificationService() {
    super(NotificationService.class.getSimpleName());
  }

  @UiThread
  public void notifyUser(String text) {
    Intent resultIntent = new Intent(this, MainActivity_.class);
    resultIntent.setAction("android.intent.action.MAIN");
    resultIntent.addCategory("android.intent.category.LAUNCHER");

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(appName)
        .setContentText(text)
        .setOngoing(true)
        .setContentIntent(contentIntent);

    notificationManager.notify(NOTIFICATION_ID, builder.build());
  }

  @UiThread
  public void unnotifyUser() {
    notificationManager.cancel(NOTIFICATION_ID);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    ActionType actionType = (ActionType) intent.getSerializableExtra(ACTION);

    if (actionType == ActionType.Create)
    {
      notifyUser(intent.getStringExtra(TEXT));
    }
    else if (actionType == ActionType.Destroy) {
      unnotifyUser();
    }
  }
}
