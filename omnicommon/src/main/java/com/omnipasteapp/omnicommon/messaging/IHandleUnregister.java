package com.omnipasteapp.omnicommon.messaging;

public interface IHandleUnregister {
  void handleUnregisterSuccess();

  void handleUnregisterError(String error);
}
