package com.omnipasteapp.omniapi.resources;

public interface IClippings {
  void saveAsync(String data, String registrationId, ISaveClippingCompleteHandler handler);

  void getLastAsync(IFetchClippingCompleteHandler handler);
}
