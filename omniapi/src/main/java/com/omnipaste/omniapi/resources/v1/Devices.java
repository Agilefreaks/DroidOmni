package com.omnipaste.omniapi.resources.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;
import rx.concurrency.Schedulers;

public class Devices {

  private interface DevicesApi {
    @Headers({
        "CONTENT_TYPE: application/json",
        "ACCEPT: application/json",
        "User-Agent: OmniApi"
    })
    @POST("/v1/devices.json")
    Observable<RegisteredDeviceDto> create(@Header("CHANNEL") String channel, @Body RegisteredDeviceDto deviceDto);
  }

  private DevicesApi devicesApi;

  public Devices(String baseUrl) {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    RestAdapter restAdapter = new RestAdapter.Builder()
        .setServer(baseUrl)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(gson))
        .build();

    devicesApi = restAdapter.create(DevicesApi.class);
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier) {
    return devicesApi.create(channel, new RegisteredDeviceDto(identifier)).subscribeOn(Schedulers.threadPoolForIO());
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier, final String name) {
    return devicesApi.create(channel, new RegisteredDeviceDto(identifier, name)).subscribeOn(Schedulers.threadPoolForIO());
  }
}