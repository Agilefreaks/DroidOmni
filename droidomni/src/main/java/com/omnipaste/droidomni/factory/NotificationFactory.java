package com.omnipaste.droidomni.factory;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.presenter.OmniPresenter;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationFactory {
  public static final int NOTIFICATION_ID = 42;
  public static final int LARGE_TEXT = 128;

  private String appName;
  private SmartActionFactory smartActionFactory;

  @Inject
  public NotificationFactory(SmartActionFactory smartActionFactory) {
    this.smartActionFactory = smartActionFactory;
  }

  public Notification buildUserNotification(Context context, String appName, String text) {
    this.appName = appName;
    NotificationCompat.Builder builder = basicBuilder(context, text);
    builder = setMinimumPriority(setSecretVisibility(builder));

    return builder.build();
  }

  public Notification buildSimpleNotification(Context context) {
    NotificationCompat.Builder builder = basicBuilder(context, "");
    builder = setMinimumPriority(setSecretVisibility(builder));

    return builder.build();
  }

  public Notification buildSimpleNotification(Context context, ClippingDto clippingDto) {
    NotificationCompat.Builder builder = basicBuilder(context, clippingDto.getContent())
        .addAction(smartActionFactory.getRemoveAction());
    builder = setPublicVisibility(builder);

    return builder.build();
  }

  public Notification buildSmartActionNotification(Context context, ClippingDto clippingDto) {
    NotificationCompat.Builder builder =
        basicBuilder(context, clippingDto.getContent())
            .setWhen(0)
            .addAction(smartActionFactory.getAction(clippingDto))
            .addAction(smartActionFactory.getRemoveAction());

    if (clippingDto.getContentLength() > LARGE_TEXT) {
      builder = builder.setStyle(new NotificationCompat.BigTextStyle().bigText(clippingDto.getContent()));
    }

    builder = setPublicVisibility(builder);

    return builder.build();
  }

  private NotificationCompat.Builder basicBuilder(Context context, String text) {
    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, OmniPresenter.getIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);

    return new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_omni)
        .setContentTitle(appName)
        .setContentText(text)
        .setContentIntent(contentIntent);
  }


  @TargetApi(21)
  public static NotificationCompat.Builder setSecretVisibility(NotificationCompat.Builder builder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return builder.setVisibility(Notification.VISIBILITY_SECRET);
    }

    return builder;
  }

  @TargetApi(21)
  public static NotificationCompat.Builder setMinimumPriority(NotificationCompat.Builder builder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return builder.setPriority(Notification.PRIORITY_MIN);
    }

    return builder;
  }

  @TargetApi(21)
  public static NotificationCompat.Builder setPublicVisibility(NotificationCompat.Builder builder) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return builder.setVisibility(Notification.VISIBILITY_PUBLIC);
    }

    return builder;
  }
}
