package com.omnipaste.droidomni.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.domain.GcmNotification;
import com.omnipaste.droidomni.receiver.GcmBroadcastReceiver;
import com.omnipaste.omnicommon.dto.NotificationDto;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import org.androidannotations.annotations.EService;

import javax.inject.Inject;

@EService
public class GcmIntentService extends IntentService {
  private static final String TAG = "GCMIntentService";

  @Inject
  public NotificationProvider gcmNotificationProvider;

  public GcmIntentService() {
    super(TAG);
    DroidOmniApplication.inject(this);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Bundle extras = intent.getExtras();
    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
    String messageType = gcm.getMessageType(intent);

    if (messageType != null) {
      switch (messageType) {
        case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR:
          Log.i(TAG, "Send error: " + (extras == null ? "" : extras.toString()));
          break;
        case GoogleCloudMessaging.MESSAGE_TYPE_DELETED:
          Log.i(TAG, "Deleted messages on server: " + (extras == null ? "" : extras.toString()));
          break;
        case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:
          GcmNotification gcmNotification = new GcmNotification(extras);
          gcmNotificationProvider.post(new NotificationDto(
              gcmNotification.getProvider(),
              gcmNotification.getRegistrationId(),
              gcmNotification.getExtras()));
          break;
      }
    }

    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
  }
}
