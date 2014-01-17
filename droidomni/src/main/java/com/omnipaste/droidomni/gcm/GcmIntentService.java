package com.omnipaste.droidomni.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.androidannotations.annotations.EService;

@EService
public class GcmIntentService extends IntentService {
  private static final String TAG = "GCMIntentService";

  public GcmIntentService() {
    super(TAG);
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
          // message received
          // messagingService.handleMessage(extras);
          break;
      }
    }

    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
  }
}
