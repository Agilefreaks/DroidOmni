package com.omnipaste.droidomni.services;

import android.app.Notification;
import android.content.Context;

import com.omnipaste.omnicommon.dto.ClippingDto;

public interface NotificationService {
  public Notification buildUserNotification(Context context, String appName, String text);

  public Notification buildSimpleNotification(Context context, ClippingDto clippingDto);

  public Notification buildSmartActionNotification(Context context, ClippingDto clippingDto);
}
