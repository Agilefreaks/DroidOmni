package com.omnipasteapp.omniclipboard.messaging;

public interface IMessagingService {
  boolean connect(String channel, IMessageHandler messageHandler);

  void disconnect(String channel);

  void sendAsync(String channel, String message, IMessageHandler messageHandler);
}
