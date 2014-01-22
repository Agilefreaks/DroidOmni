package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import rx.Observable;
import rx.concurrency.Schedulers;

public class Devices extends Resource {

  private interface DevicesApi {
    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT})
    @POST("/v1/devices.json")
    Observable<RegisteredDeviceDto> create(@Header("CHANNEL") String channel, @Body RegisteredDeviceDto deviceDto);

    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT})
    @PUT("/v1/devices/activate.json")
    Observable<RegisteredDeviceDto> activate(@Header("CHANNEL") String channel, @Body RegisteredDeviceDto deviceDto);
  }

  private DevicesApi devicesApi;

  public Devices(String baseUrl) {
    super(baseUrl);

    devicesApi = restAdapter.create(DevicesApi.class);
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier) {
    return devicesApi.create(channel, new RegisteredDeviceDto(identifier)).subscribeOn(Schedulers.threadPoolForIO());
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier, final String name) {
    return devicesApi.create(channel, new RegisteredDeviceDto(identifier, name)).subscribeOn(Schedulers.threadPoolForIO());
  }

  public Observable<RegisteredDeviceDto> activate(final String channel, final String identifier, String registrationId) {
    RegisteredDeviceDto deviceDto = new RegisteredDeviceDto();
    deviceDto.setIdentifier(identifier);
    deviceDto.setRegistrationId(registrationId);
    deviceDto.setProvider("gcm");

    return devicesApi.activate(channel, deviceDto).subscribeOn(Schedulers.threadPoolForIO());
  }
}