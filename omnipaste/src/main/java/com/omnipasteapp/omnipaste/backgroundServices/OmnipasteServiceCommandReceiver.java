package com.omnipasteapp.omnipaste.backgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnipaste.services.IntentService;

@EReceiver
public class OmnipasteServiceCommandReceiver extends BroadcastReceiver {
  @StringRes
  public String startOmnipasteService;

  @Override
  public void onReceive(Context context, Intent intent) {
    if(startOmnipasteService.equals(intent.getAction())) {
      IntentService.startService(context, OmnipasteService_.class);
    }
    else {
      IntentService.stopService(context, OmnipasteService_.class);
    }
  }
}
