package com.omnipasteapp.omnipaste.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.ClippingType;
import com.omnipasteapp.omnipaste.R;
import com.omnipasteapp.omnipaste.activities.MainActivity_;

@EService
public class NotificationService extends IntentService {

  public static final int NOTIFICATION_ID = 42;

  public static final String ACTION = "com.omnipasteapp.omnipaste.services.ACTION";
  public static final String TEXT = "com.omnipasteapp.omnipaste.services.TEXT";
  public static final String CLIPPING = "com.omnipasteapp.omnipaste.services.CLIPPING";

  public enum ActionType {
    Create,
    Destroy,
    Update
  }

  @StringRes
  public String appName;

  @StringRes
  public String call;

  @SystemService
  public NotificationManager notificationManager;

  public NotificationService() {
    super(NotificationService.class.getSimpleName());
  }

  @UiThread
  public void notifyUser(String text) {
    notificationManager.notify(NOTIFICATION_ID, basicBuilder(text).build());
  }

  @UiThread
  public void unnotifyUser() {
    notificationManager.cancel(NOTIFICATION_ID);
  }

  @UiThread
  public void update(Clipping clipping) {
    NotificationCompat.Builder builder = basicBuilder(clipping.getContent());

    if (clipping.getType() == ClippingType.PhoneNumber) {
      Intent callIntent = new Intent(Intent.ACTION_CALL);
      callIntent.setData(Uri.parse("tel:" + clipping.getContent()));

      builder = builder
          .setStyle(new NotificationCompat.BigTextStyle().bigText(clipping.getContent()))
          .addAction(R.drawable.ic_action_call_light, call, PendingIntent.getActivity(this, 0, callIntent, 0));
    }

    notificationManager.notify(NOTIFICATION_ID, builder.build());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    ActionType actionType = (ActionType) intent.getSerializableExtra(ACTION);

    if (actionType == ActionType.Create) {
      notifyUser(intent.getStringExtra(TEXT));
    } else if (actionType == ActionType.Destroy) {
      unnotifyUser();
    } else if (actionType == ActionType.Update) {
      update((Clipping) intent.getParcelableExtra(CLIPPING));
    }
  }

  private NotificationCompat.Builder basicBuilder(String text) {
    Intent resultIntent = new Intent(this, MainActivity_.class);
    resultIntent.setAction("android.intent.action.MAIN");
    resultIntent.addCategory("android.intent.category.LAUNCHER");

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

    return new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(appName)
        .setContentText(text)
        .setOngoing(true)
        .setContentIntent(contentIntent);
  }
}
