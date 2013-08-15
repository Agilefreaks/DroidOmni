package com.omnipasteapp.omniclipboard.api;

public interface ISaveClippingCompleteHandler {
  void saveClippingSucceeded();

  void saveClippingFailed(String reason);
}
