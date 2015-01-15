package com.omnipaste.droidomni.interaction;

import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.DeviceDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CreateDevice {
  private final String name;
  private final Devices devices;

  @Inject
  public CreateDevice(@DeviceName String name, Devices devices) {
    this.name = name;
    this.devices = devices;
  }

  public Observable<DeviceDto> run() {
    return devices.create(name);
  }
}
