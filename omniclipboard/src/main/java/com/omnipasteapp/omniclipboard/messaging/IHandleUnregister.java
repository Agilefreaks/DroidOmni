package com.omnipasteapp.omniclipboard.messaging;

public interface IHandleUnregister {
  void handleUnregisterSuccess();

  void handleUnregisterError(String error);
}
