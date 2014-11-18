package com.omnipaste.droidomni.service;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;

public class OmniIncomingHandler extends Handler {
  public static final int MSG_REGISTER_CLIENT = 1;
  public static final int MSG_UNREGISTER_CLIENT = 2;
  public static final int MSG_CREATE_ERROR = 3;
  public static final int MSG_STARTED = 4;

  private WeakReference<OmniService> omniService;

  public OmniIncomingHandler(OmniService omniService) {
    this.omniService = new WeakReference<>(omniService);
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_REGISTER_CLIENT:
        omniService.get().addClient(msg.replyTo);

        if (omniService.get().isStarted()) {
          sendStartedToClient(msg.replyTo);
        }

        break;
      case MSG_UNREGISTER_CLIENT:
        omniService.get().removeClient(msg.replyTo);
        break;
      default:
        super.handleMessage(msg);
    }
  }

  private void sendStartedToClient(Messenger client) {
    Message message = Message.obtain(null, OmniIncomingHandler.MSG_STARTED);
    try {
      client.send(message);
    } catch (RemoteException ignored) {
    }
  }
}
