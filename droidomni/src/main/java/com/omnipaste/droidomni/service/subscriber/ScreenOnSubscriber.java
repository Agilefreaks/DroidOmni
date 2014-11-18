package com.omnipaste.droidomni.service.subscriber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.omnipaste.droidomni.interaction.Refresh;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScreenOnSubscriber extends BroadcastReceiver implements Subscriber {
  private final Context context;
  private final Refresh refresh;

  @Inject
  public ScreenOnSubscriber(Context context, Refresh refresh) {
    this.context = context;
    this.refresh = refresh;
  }

  @Override
  public void start(String deviceIdentifier) {
    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    context.registerReceiver(this, filter);
  }

  @Override
  public void stop() {
    context.unregisterReceiver(this);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    refresh.all();
  }
}
