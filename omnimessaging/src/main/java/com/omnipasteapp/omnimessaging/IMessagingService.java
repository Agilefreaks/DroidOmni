package com.omnipasteapp.omnimessaging;

import android.os.Bundle;

import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;

public interface IMessagingService {
  boolean connect(String channel);

  void disconnect(String channel);

  String getRegistrationId();

  void setClipboardProvider(IClipboardProvider clipboardProvider);

  void handleMessage(Bundle extras);
}
