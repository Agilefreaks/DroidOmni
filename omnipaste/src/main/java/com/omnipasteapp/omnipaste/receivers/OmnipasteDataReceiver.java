package com.omnipasteapp.omnipaste.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.omnipasteapp.omnipaste.activities.IOmnipasteDataDisplay;
import com.omnipasteapp.omnipaste.backgroundServices.OmnipasteService;
import com.omnipasteapp.omnipaste.enums.Sender;

public class OmnipasteDataReceiver extends BroadcastReceiver {

  public IOmnipasteDataDisplay _display;

  public OmnipasteDataReceiver(IOmnipasteDataDisplay display) {
    _display = display;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    Sender sender = (Sender) intent.getSerializableExtra(OmnipasteService.EXTRA_CLIPBOARD_SENDER);
    String data = intent.getStringExtra(OmnipasteService.EXTRA_CLIPBOARD_DATA);

    _display.omnipasteDataReceived(data, sender);
  }
}
