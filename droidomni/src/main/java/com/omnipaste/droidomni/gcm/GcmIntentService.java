package com.omnipaste.droidomni.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.events.GcmEvent;

import org.androidannotations.annotations.EService;

import de.greenrobot.event.EventBus;

@EService
public class GcmIntentService extends IntentService {
  private EventBus eventBus = EventBus.getDefault();
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
          eventBus.post(new GcmEvent(extras));
          break;
      }
    }

    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
  }
}