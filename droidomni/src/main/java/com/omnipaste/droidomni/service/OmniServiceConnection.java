package com.omnipaste.droidomni.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OmniServiceConnection implements ServiceConnection {
  private final Context context;
  private Messenger omniServiceMessenger;

  @Inject
  public OmniServiceConnection(Context context) {
    this.context = context;
  }

  @Override
  public void onServiceConnected(ComponentName componentName, IBinder service) {
    omniServiceMessenger = new Messenger(service);
  }

  @Override
  public void onServiceDisconnected(ComponentName componentName) {
    omniServiceMessenger = null;
  }

  public Messenger getOmniServiceMessenger() {
    return omniServiceMessenger;
  }

  public void startOmniService() {
    context.bindService(OmniService.getIntent(), this, Context.BIND_AUTO_CREATE);
  }

  public void stopOmniService() {
    context.unbindService(this);
  }

  public void restartOmniService() {
    stopOmniService();
    startOmniService();
  }
}
