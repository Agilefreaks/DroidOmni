package com.omnipasteapp.messaging;

import com.pubnub.api.Callback;

public class MessageReceivedCallback extends Callback {
  private final IMessageHandler handler;

  public MessageReceivedCallback(IMessageHandler handler) {
    this.handler = handler;
  }

  @Override
  public void successCallback(String s, Object o) {
    handler.messageReceived(o.toString());
  }
}
