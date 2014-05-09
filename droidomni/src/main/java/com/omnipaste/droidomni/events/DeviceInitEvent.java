package com.omnipaste.droidomni.events;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

public class DeviceInitEvent {
  private RegisteredDeviceDto registeredDeviceDto;

  public DeviceInitEvent(RegisteredDeviceDto registeredDeviceDto) {
    this.registeredDeviceDto = registeredDeviceDto;
  }

  public RegisteredDeviceDto getRegisteredDeviceDto() {
    return registeredDeviceDto;
  }
}
