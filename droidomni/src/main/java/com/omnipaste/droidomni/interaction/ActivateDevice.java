package com.omnipaste.droidomni.interaction;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.prefs.DeviceId;
import com.omnipaste.droidomni.prefs.GcmSenderId;
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
public class ActivateDevice {
  private final Devices devices;
  private final StringPreference deviceId;
  private final StringPreference gcmSenderId;
  private final GoogleCloudMessaging googleCloudMessaging;

  @Inject
  public ActivateDevice(Devices devices,
                        @DeviceId StringPreference deviceId,
                        @GcmSenderId StringPreference gcmSenderId,
                        GoogleCloudMessaging googleCloudMessaging) {
    this.devices = devices;
    this.deviceId = deviceId;
    this.gcmSenderId = gcmSenderId;
    this.googleCloudMessaging = googleCloudMessaging;
  }

  public Observable<DeviceDto> run() {
    return registerToGcm().flatMap(new Func1<String, Observable<? extends DeviceDto>>() {
      @Override
      public Observable<? extends DeviceDto> call(String registrationId) {
        return activateDevice(registrationId);
      }
    });
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

  private Observable<DeviceDto> activateDevice(String registrationId) {
    return devices.activate(deviceId.get(), registrationId);
  }
}
