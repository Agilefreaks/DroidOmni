package com.omnipasteapp.omnipaste;

import android.content.Context;
import android.content.Intent;
import com.google.inject.Inject;
import com.omnipasteapp.omnipaste.enums.BackgroundServiceStates;
import com.omnipasteapp.omnipaste.services.IIntentService;
import roboguice.receiver.RoboBroadcastReceiver;

public class BackgroundServiceCommandReceiver extends RoboBroadcastReceiver {

  public static final String START_SERVICE = "start_background_service";
  public static final String STOP_SERVICE = "stop_background_service";

  @Inject
  private IIntentService _intentService;

  @Override
  protected void handleReceive(Context context, Intent intent) {
    super.handleReceive(context, intent);

    onHandleReceive(context, intent);
  }

  public void onHandleReceive(Context context, Intent intent) {
      if (START_SERVICE.equals(intent.getAction())) {
        if (BackgroundService.serviceState == BackgroundServiceStates.Inactive) {
          _intentService.startService(BackgroundService.class);
        }
    } else {
      _intentService.stopService(BackgroundService.class);
    }
  }
}
