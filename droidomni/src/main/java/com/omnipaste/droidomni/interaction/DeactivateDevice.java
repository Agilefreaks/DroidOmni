package com.omnipaste.droidomni.interaction;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.prefs.DeviceId;
import com.omnipaste.omniapi.resource.v1.user.Devices;
import com.omnipaste.omnicommon.dto.DeviceDto;
import com.omnipaste.omnicommon.prefs.StringPreference;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class DeactivateDevice {
  private final Devices devices;
  private StringPreference deviceId;
  private GoogleCloudMessaging googleCloudMessaging;

  @Inject
  public DeactivateDevice(
      Devices devices,
      @DeviceId StringPreference deviceId,
      GoogleCloudMessaging googleCloudMessaging) {
    this.devices = devices;
    this.deviceId = deviceId;
    this.googleCloudMessaging = googleCloudMessaging;
  }

  public Observable<Object> run() {
    return deactivateDevice().flatMap(new Func1<DeviceDto, Observable<?>>() {
      @Override
      public Observable<?> call(DeviceDto deviceDto) {
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

  public Observable<DeviceDto> deactivateDevice() {
    return devices.deactivate(deviceId.get());
  }
}
