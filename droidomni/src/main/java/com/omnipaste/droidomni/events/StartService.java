package com.omnipaste.droidomni.events;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

public class StartService {
  public RegisteredDeviceDto deviceDto;

  public StartService(RegisteredDeviceDto deviceDto) {

    this.deviceDto = deviceDto;
  }
}
