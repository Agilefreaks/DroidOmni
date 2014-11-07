package com.omnipaste.droidomni.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.ui.activity.OmniActivity_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationService {
  public static final int NOTIFICATION_ID = 42;
  public static final int LARGE_TEXT = 128;

  private String appName;
  private SmartActionService smartActionService;

  @Inject
  public NotificationService(SmartActionService smartActionService) {
    this.smartActionService = smartActionService;
  }

  public Notification buildUserNotification(Context context, String appName, String text) {
    this.appName = appName;
    return basicBuilder(context, text).build();
  }

  public Notification buildSimpleNotification(Context context, ClippingDto clippingDto) {
    return basicBuilder(context, clippingDto.getContent()).build();
  }

  public Notification buildSmartActionNotification(Context context, ClippingDto clippingDto) {
    NotificationCompat.Builder builder =
        basicBuilder(context, clippingDto.getContent())
            .setWhen(0)
            .setPriority(Notification.PRIORITY_MAX)
            .addAction(
                smartActionService.getIcon(clippingDto),
                smartActionService.getTitle(clippingDto),
                smartActionService.buildPendingIntent(clippingDto));

    if (clippingDto.getContentLength() > LARGE_TEXT) {
      builder = builder.setStyle(new NotificationCompat.BigTextStyle().bigText(clippingDto.getContent()));
    }

    return builder.build();
  }

  private NotificationCompat.Builder basicBuilder(Context context, String text) {
    Intent resultIntent = new Intent(context, OmniActivity_.class);
    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    return new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_omni)
        .setContentTitle(appName)
        .setContentText(text)
        .setContentIntent(contentIntent);
  }
}
