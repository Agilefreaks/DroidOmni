package com.omnipasteapp.omniclipboard.api;

public interface IOmniApi {
  void saveClippingAsync(String data, ISaveClippingCompleteHandler handler);

  void getLastClippingAsync(IGetClippingCompleteHandler handler);
}
