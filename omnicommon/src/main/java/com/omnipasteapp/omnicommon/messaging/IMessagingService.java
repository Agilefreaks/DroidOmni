package com.omnipasteapp.omnicommon.messaging;

import android.os.Bundle;

public interface IMessagingService {
  boolean connect(String channel, IMessageHandler messageHandler);

  void disconnect(String channel);

  String getRegistrationId();

  void handleMessage(Bundle extras);
}
