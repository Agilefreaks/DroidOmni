package com.omnipaste.droidomni.services;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;
import rx.util.functions.Func1;

public class DeviceService {
  private final Configuration configuration;

  @Inject
  public GoogleCloudMessaging googleCloudMessaging;

  @Inject
  public ConfigurationService configurationService;

  @Inject
  public Context context;

  @Inject
  public OmniApi omniApi;

  public DeviceService() {
    DroidOmniApplication.inject(this);

    configuration = configurationService.getConfiguration();
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

  public Observable<RegisteredDeviceDto> createDevice() {
    return omniApi.devices().create(configuration.getChannel(), getIdentifier());
  }

  public Observable<String> registerToGcm() {
    return Observable.create(new Observable.OnSubscribeFunc<String>() {
      @Override
      public Subscription onSubscribe(Observer<? super String> observer) {
        try {
          observer.onNext(googleCloudMessaging.register(configuration.getGcmSenderId()));
        } catch (IOException e) {
          observer.onError(e);
        }

        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }

  private Observable<RegisteredDeviceDto> activateDevice(String registrationId) {
    return omniApi.devices().activate(configuration.getChannel(), getIdentifier(), registrationId);
  }

  private String getIdentifier() {
    String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    return String.format("%s-%s", Build.MODEL, android_id);
  }
}
