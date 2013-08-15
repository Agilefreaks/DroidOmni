package com.omnipasteapp.omniclipboard.messaging;

import com.pubnub.api.Callback;

public class MessageSentCallback extends Callback {
  private final IMessageHandler messageHandler;

  public MessageSentCallback(IMessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @Override
  public void successCallback(String channel, Object message) {
    messageHandler.messageSent(message.toString());
  }

  @Override
  public void errorCallback(java.lang.String s, com.pubnub.api.PubnubError pubnubError) {
    messageHandler.messageSendFailed(s, pubnubError.getErrorString());
  }
}
