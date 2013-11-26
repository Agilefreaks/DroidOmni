package com.omnipasteapp.omnipaste;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

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

    if (!(extras != null && extras.isEmpty())) {
      if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
        Log.i(TAG, "Send error: " + extras.toString());
      } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
        Log.i(TAG, "Deleted messages on server: " + extras.toString());
      } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        Log.i(TAG, "Received: " + extras.toString());
      }
    }

    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
  }
}
