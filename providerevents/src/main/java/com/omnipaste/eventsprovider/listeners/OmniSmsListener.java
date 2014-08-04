package com.omnipaste.eventsprovider.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class OmniSmsListener extends BroadcastReceiver {
  private final static String EXTRAS_KEY = "pdus";

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras == null)
      return;

    Object[] pdus = (Object[]) extras.get(EXTRAS_KEY);
  }
}
