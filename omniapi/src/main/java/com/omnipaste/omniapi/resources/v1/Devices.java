package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

public class Devices {

  private interface DevicesApi {
    @POST("v1/devices")
    RegisteredDeviceDto create(@Body String channel, @Body String identifier);
  }

  private DevicesApi devicesApi;

  public Devices(String baseUrl) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setServer(baseUrl)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .build();

    devicesApi = restAdapter.create(DevicesApi.class);
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier) {
    devicesApi.create(channel, identifier);
    return Observable.create(new Observable.OnSubscribeFunc<RegisteredDeviceDto>() {
      @Override
      public Subscription onSubscribe(Observer<? super RegisteredDeviceDto> observer) {
        observer.onNext(devicesApi.create(channel, identifier));
        observer.onCompleted();

        return Subscriptions.empty();
      }
    }).subscribeOn(Schedulers.threadPoolForIO());
  }
}