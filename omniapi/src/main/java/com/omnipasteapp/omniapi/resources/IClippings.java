package com.omnipasteapp.omniapi.resources;

public interface IClippings {
  void saveAsync(String data, ISaveClippingCompleteHandler handler);

  void getLastAsync(IFetchClippingCompleteHandler handler);
}
