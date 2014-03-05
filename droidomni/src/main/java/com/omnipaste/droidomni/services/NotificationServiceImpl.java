package com.omnipaste.droidomni.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.util.HashMap;

import javax.inject.Inject;

public class NotificationServiceImpl implements NotificationService {
  public static final int NOTIFICATION_ID = 42;

  private HashMap<ClippingDto.ClippingType, Integer> smartActionIcon = new HashMap<ClippingDto.ClippingType, Integer>() {{
    put(ClippingDto.ClippingType.phoneNumber, R.drawable.ic_smart_action_phone_number_light);
    put(ClippingDto.ClippingType.webSite, R.drawable.ic_smart_action_uri_light);
  }};

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
  public Notification buildSimpleNotification(Context context, String text) {
    return basicBuilder(context, text).build();
  }

  @Override
  public Notification buildSmartActionNotification(Context context, ClippingDto clippingDto) {
    NotificationCompat.Builder builder =
        basicBuilder(context, clippingDto.getContent())
            .setStyle(new NotificationCompat.BigTextStyle().bigText(clippingDto.getContent()))
            .addAction(smartActionIcon.get(clippingDto.getType()),
                "Call",
                PendingIntent.getActivity(context, 0, smartActionService.buildIntent(clippingDto), 0));

    return builder.build();
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
