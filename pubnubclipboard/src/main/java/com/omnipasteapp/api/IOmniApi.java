package com.omnipasteapp.api;

public interface IOmniApi {
  void saveClippingAsync(String data, ISaveClippingCompleteHandler handler);

  void getLastClippingAsync(IGetClippingCompleteHandler handler);
}
