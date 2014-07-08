package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import rx.Observable;

public interface DeviceService {
  Observable<RegisteredDeviceDto> init();
}
