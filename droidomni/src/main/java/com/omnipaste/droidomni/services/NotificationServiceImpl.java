package com.omnipaste.droidomni.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.omnicommon.dto.ClippingDto;

public class NotificationServiceImpl implements NotificationService {

  public static final int NOTIFICATION_ID = 42;

  public String appName;

  @Override
  public Notification buildUserNotification(Context context, String appName, String text) {
    this.appName = appName;
    return basicBuilder(context, text).build();
  }

  @Override
  public Notification buildUserNotification(Context context, String text) {
    return basicBuilder(context, text).build();
  }

  public Notification buildSmartActionNotification(ClippingDto clipping) {
    return null;
//    NotificationCompat.Builder builder = basicBuilder(clipping.getContent());
//
//    if (clipping.getType() == ClippingDto.ClippingType.phoneNumber) {
//      Intent callIntent = new Intent(Intent.ACTION_CALL);
//      callIntent.setData(Uri.parse("tel:" + clipping.getContent()));
//
//      builder = builder
//          .setStyle(new NotificationCompat.BigTextStyle().bigText(clipping.getContent()))
//          .addAction(R.drawable.ic_action_call_light, call, PendingIntent.getActivity(this, 0, callIntent, 0));
//    }
//
//    notificationManager.notify(NOTIFICATION_ID, builder.build());
  }

  private NotificationCompat.Builder basicBuilder(Context context, String text) {
    Intent resultIntent = new Intent(context, MainActivity_.class);
    resultIntent.setAction("android.intent.action.MAIN");
    resultIntent.addCategory("android.intent.category.LAUNCHER");

    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

    return new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_clipboard)
        .setContentTitle(appName)
        .setContentText(text)
        .setOngoing(true)
        .setContentIntent(contentIntent);
  }
}
