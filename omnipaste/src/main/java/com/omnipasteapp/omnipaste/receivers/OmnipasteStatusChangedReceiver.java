package com.omnipasteapp.omnipaste.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.omnipasteapp.omnipaste.activities.IOmnipasteStatusChangedDisplay;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService;

public class OmnipasteStatusChangedReceiver extends BroadcastReceiver {

  private IOmnipasteStatusChangedDisplay _display;

  public OmnipasteStatusChangedReceiver(IOmnipasteStatusChangedDisplay display) {
    _display = display;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    boolean started = intent.getBooleanExtra(OmnipasteService.EXTRA_STARTED, false);

    if (started) {
      _display.omnipasteServiceStarted();
    }
    else {
      _display.omnipasteServiceStopped();
    }
  }
}
