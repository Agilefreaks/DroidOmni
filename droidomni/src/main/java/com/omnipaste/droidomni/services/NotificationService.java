package com.omnipaste.droidomni.services;

import android.app.Notification;
import android.content.Context;

public interface NotificationService {
  public Notification buildUserNotification(Context context, String appName, String text);

  public Notification buildUserNotification(Context context, String text);
}
