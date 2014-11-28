package com.omnipaste.droidomni.interaction;

import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CreateDevice {
  private final String identifier;
  private final Devices devices;

  @Inject
  public CreateDevice(@DeviceIdentifier String identifier, Devices devices) {
    this.identifier = identifier;
    this.devices = devices;
  }

  public Observable<RegisteredDeviceDto> run() {
    return devices.create(identifier);
  }
}
