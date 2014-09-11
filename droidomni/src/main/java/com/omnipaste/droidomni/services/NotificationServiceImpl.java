package com.omnipaste.droidomni.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.OmniActivity_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;

public class  NotificationServiceImpl implements NotificationService {
  public static final int NOTIFICATION_ID = 42;

  private String appName;
  private SmartActionService smartActionService;

  @Inject
  public NotificationServiceImpl(SmartActionService smartActionService) {
    this.smartActionService = smartActionService;
  }

  @Override
  public Notification buildUserNotification(Context context, String appName, String text) {
    this.appName = appName;
    return basicBuilder(context, text).build();
  }

  @Override
  public Notification buildSimpleNotification(Context context, ClippingDto clippingDto) {
    return basicBuilder(context, clippingDto.getContent()).build();
  }

  @Override
  public Notification buildSmartActionNotification(Context context, ClippingDto clippingDto) {
    NotificationCompat.Builder builder =
        basicBuilder(context, clippingDto.getContent())
            .setWhen(0)
            .setPriority(Notification.PRIORITY_MAX)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(clippingDto.getContent()))
            .addAction(
                smartActionService.getIcon(clippingDto),
                smartActionService.getTitle(clippingDto),
                smartActionService.buildPendingIntent(clippingDto));

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
