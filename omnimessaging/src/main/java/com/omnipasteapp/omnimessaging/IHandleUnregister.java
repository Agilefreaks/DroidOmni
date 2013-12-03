package com.omnipasteapp.omnimessaging;

public interface IHandleUnregister {
  void handleUnregisterSuccess();

  void handleUnregisterError(String error);
}
