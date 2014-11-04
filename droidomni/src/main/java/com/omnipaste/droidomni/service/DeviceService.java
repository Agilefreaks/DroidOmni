package com.omnipaste.droidomni.service;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

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
public class DeviceService {
  private Devices devices;
  private StringPreference gcmSenderId;
  private GoogleCloudMessaging googleCloudMessaging;
  private Context applicationContext;

  @Inject
  public DeviceService(Devices devices, @GcmSenderId StringPreference gcmSenderId,
                       GoogleCloudMessaging googleCloudMessaging, Context applicationContext) {
    this.devices = devices;
    this.gcmSenderId = gcmSenderId;
    this.googleCloudMessaging = googleCloudMessaging;
    this.applicationContext = applicationContext;
  }

  public Observable<RegisteredDeviceDto> init() {
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
    return devices.create(getIdentifier());
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
    return devices.activate(getIdentifier(), registrationId);
  }

  private String getIdentifier() {
    String android_id = Settings.Secure.getString(applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    return String.format("%s-%s", Build.MODEL, android_id);
  }
}
