package com.omnipaste.droidomni.service;

import android.os.Handler;
import android.os.Message;

public class OmniIncomingHandler extends Handler {
  public static final int MSG_CREATE_ERROR = 3;
  public static final int MSG_STARTED = 4;
  public static final int MSG_STOPPED = 5;
  private OmniServiceConnection omniServiceConnection;

  public OmniIncomingHandler(OmniServiceConnection omniServiceConnection) {
    this.omniServiceConnection = omniServiceConnection;
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_STARTED:
        omniServiceConnection.serviceStarted();
        break;
      case MSG_STOPPED:
        omniServiceConnection.serviceStopped();
        break;
      case MSG_CREATE_ERROR:
        omniServiceConnection.serviceError((Throwable) msg.obj);
        break;
      default:
        super.handleMessage(msg);
    }
  }
}