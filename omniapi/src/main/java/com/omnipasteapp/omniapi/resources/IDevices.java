package com.omnipasteapp.omniapi.resources;

public interface IDevices {
  void saveAsync(String data, ISaveDeviceCompleteHandler handler);

  void deleteAsync(String registrationId, IDeleteDeviceCompleteHandler handler);
}
