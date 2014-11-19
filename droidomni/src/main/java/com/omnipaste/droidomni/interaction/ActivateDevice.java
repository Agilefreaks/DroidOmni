package com.omnipaste.droidomni.interaction;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.prefs.GcmSenderId;
import com.omnipaste.omniapi.resource.v1.Devices;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.prefs.StringPreference;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class ActivateDevice {
  private final Devices devices;
  private final StringPreference gcmSenderId;
  private final GoogleCloudMessaging googleCloudMessaging;
  private String identifier;

  @Inject
  public ActivateDevice(Devices devices,
                        @GcmSenderId StringPreference gcmSenderId,
                        GoogleCloudMessaging googleCloudMessaging,
                        @DeviceIdentifier String identifier) {
    this.devices = devices;
    this.gcmSenderId = gcmSenderId;
    this.googleCloudMessaging = googleCloudMessaging;
    this.identifier = identifier;
  }

  public Observable<RegisteredDeviceDto> run() {
    return createDevice().flatMap(new Func1<RegisteredDeviceDto, Observable<RegisteredDeviceDto>>() {
      @Override
      public Observable<RegisteredDeviceDto> call(final RegisteredDeviceDto deviceDto) {
        return registerToGcm().flatMap(new Func1<String, Observable<? extends RegisteredDeviceDto>>() {
          @Override
          public Observable<? extends RegisteredDeviceDto> call(String registrationId) {
            return activateDevice(registrationId);
          }
        });
      }
    });
  }

  private Observable<RegisteredDeviceDto> createDevice() {
    return devices.create(identifier);
  }

  private Observable<String> registerToGcm() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override
      public void call(Subscriber<? super String> subscriber) {
        try {
          subscriber.onNext(googleCloudMessaging.register(gcmSenderId.get()));
        } catch (IOException e) {
          subscriber.onError(e);
        }

        subscriber.onCompleted();
      }
    });
  }

  private Observable<RegisteredDeviceDto> activateDevice(String registrationId) {
    return devices.activate(identifier, registrationId);
  }
}
