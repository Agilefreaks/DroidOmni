package com.omnipasteapp.api;

public interface ISaveClippingCompleteHandler {
  void saveClippingSucceeded();

  void saveClippingFailed(String reason);
}
