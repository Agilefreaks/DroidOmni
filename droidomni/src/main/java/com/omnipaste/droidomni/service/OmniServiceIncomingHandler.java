package com.omnipaste.droidomni.service;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class OmniServiceIncomingHandler extends Handler {
  public static final int MSG_REGISTER_CLIENT = 1;
  public static final int MSG_UNREGISTER_CLIENT = 2;

  private List<Messenger> clients = new ArrayList<>();
  private WeakReference<OmniService> omniService;

  public OmniServiceIncomingHandler(OmniService omniService) {
    this.omniService = new WeakReference<>(omniService);
  }

  @Override
  public void handleMessage(Message msg) {
    switch (msg.what) {
      case MSG_REGISTER_CLIENT:
        clients.add(msg.replyTo);

        if (omniService.get().isStarted()) {
          sendStartedToClients();
        }

        break;
      case MSG_UNREGISTER_CLIENT:
        clients.remove(msg.replyTo);
        break;
      default:
        super.handleMessage(msg);
    }
  }

  public void sendErrorToClients(Throwable throwable) {
    sendMessageToClients(OmniIncomingHandler.MSG_CREATE_ERROR, throwable);
  }

  public void sendStartedToClients() {
    sendMessageToClients(OmniIncomingHandler.MSG_STARTED);
  }

  public void sendStoppedToClients() {
    sendMessageToClients(OmniIncomingHandler.MSG_STOPPED);
  }

  private void sendMessageToClients(int what) {
    sendMessageToClients(what, null);
  }

  private void sendMessageToClients(int what, Object obj) {
    Message message = Message.obtain(null, what);
    message.obj = obj;
    for (Messenger client : clients) {
      try {
        client.send(message);
      } catch (RemoteException e) {
        clients.remove(client);
      }
    }
  }
}
