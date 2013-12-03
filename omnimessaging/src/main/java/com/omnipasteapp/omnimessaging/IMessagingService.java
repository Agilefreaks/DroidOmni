package com.omnipasteapp.omnimessaging;

import android.os.Bundle;

public interface IMessagingService {
  boolean connect(String channel);

  void disconnect(String channel);

  String getRegistrationId();

  void handleMessage(Bundle extras);
}
