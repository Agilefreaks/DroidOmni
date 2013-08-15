package com.omnipasteapp.messaging;

public interface IMessageHandler {
  void messageReceived(String message);

  void messageSent(String message);

  void messageSendFailed(String message, String reason);
}
