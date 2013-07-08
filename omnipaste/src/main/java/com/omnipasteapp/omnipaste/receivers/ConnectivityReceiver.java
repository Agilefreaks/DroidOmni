package com.omnipasteapp.omnipaste.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnipaste.services.IntentService;

@EReceiver
public class ConnectivityReceiver extends BroadcastReceiver {
  @StringRes
  public String startOmniService;

  @StringRes
  public String stopOmniService;

  @Override
  public void onReceive(Context context, Intent intent) {
    boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

    if (noConnectivity) {
      IntentService.sendBroadcast(context, stopOmniService);
    }
    else {
      IntentService.sendBroadcast(context, startOmniService);
    }
  }
}