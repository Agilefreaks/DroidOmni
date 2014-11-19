package com.omnipaste.droidomni.interaction;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class DeactivateDevice {
  private final Devices devices;
  private final String identifier;
  private GoogleCloudMessaging googleCloudMessaging;

  @Inject
  public DeactivateDevice(
      Devices devices,
      @DeviceIdentifier String identifier,
      GoogleCloudMessaging googleCloudMessaging) {
    this.devices = devices;
    this.identifier = identifier;
    this.googleCloudMessaging = googleCloudMessaging;
  }

  public Observable<Object> run() {
    return deactivateDevice().flatMap(new Func1<RegisteredDeviceDto, Observable<?>>() {
      @Override
      public Observable<?> call(RegisteredDeviceDto registeredDeviceDto) {
        return unregisterToGcm();
      }
    });
  }

  public Observable unregisterToGcm() {
    return Observable.create(new Observable.OnSubscribe<Object>() {
      @Override
      public void call(Subscriber<? super Object> subscriber) {
        try {
          googleCloudMessaging.unregister();
          subscriber.onNext(null);
        } catch (IOException ignore) {
        }
        subscriber.onCompleted();
      }
    });
  }

  public Observable<RegisteredDeviceDto> deactivateDevice() {
    return devices.deactivate(identifier);
  }
}
