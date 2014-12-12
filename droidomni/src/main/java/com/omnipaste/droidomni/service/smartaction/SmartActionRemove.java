package com.omnipaste.droidomni.service.smartaction;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.R;
import com.omnipaste.droidomni.factory.NotificationFactory;

import javax.inject.Inject;

public class SmartActionRemove extends BroadcastReceiver {

  @Inject
  public NotificationFactory notificationFactory;

  @Inject
  public NotificationManagerCompat notificationManager;

  public SmartActionRemove() {
    DroidOmniApplication.inject(this);
  }

  public int getTitle() {
    return R.string.smart_action_remove;
  }

  public int[] getIcon() {
    return new int[] { R.drawable.ic_remove_light, R.drawable.ic_remove};
  }

  public PendingIntent buildIntent(Context context) {
    Intent removeIntent = new Intent(context, SmartActionRemove.class);

    return PendingIntent.getBroadcast(context, 0, removeIntent, 0);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    notificationManager.notify(NotificationFactory.NOTIFICATION_ID, notificationFactory.buildSimpleNotification(context));
  }
}
