package com.omnipaste.droidomni.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.service.OmniServiceConnection;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;

import javax.inject.Inject;

@EReceiver
public class StartOmniAtBootReceiver extends BroadcastReceiver {
  @Inject
  public OmniServiceConnection omniServiceConnection;

  public StartOmniAtBootReceiver() {
    DroidOmniApplication.inject(this);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    // empty, will be overridden in generated subclass
  }

  @ReceiverAction(Intent.ACTION_BOOT_COMPLETED)
  public void actionBootCompleted() {
    omniServiceConnection.startOmniService().subscribe();
  }
}
