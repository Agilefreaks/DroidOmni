package com.omnipasteapp.omnipaste.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.res.StringRes;
import com.omnipasteapp.omnipaste.services.IntentHelper;
import com.omnipasteapp.omnipaste.services.OmnipasteService_;

@EReceiver
public class OmnipasteServiceReceiver extends BroadcastReceiver {
  @StringRes
  public String startOmnipasteService;

  @Override
  public void onReceive(Context context, Intent intent) {
    if(startOmnipasteService.equals(intent.getAction())) {
      IntentHelper.startService(context, OmnipasteService_.class);
    } else {
      IntentHelper.stopService(context, OmnipasteService_.class);
    }
  }
}
