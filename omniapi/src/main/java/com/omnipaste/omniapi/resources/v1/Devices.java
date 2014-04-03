package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import rx.Observable;

public class Devices extends Resource {
  private interface DevicesApi {
    @POST("/v1/devices.json")
    Observable<RegisteredDeviceDto> create(@Header("Authorization") String token, @Body RegisteredDeviceDto deviceDto);

    @PUT("/v1/devices/activate.json")
    Observable<RegisteredDeviceDto> activate(@Header("Authorization") String token, @Body RegisteredDeviceDto deviceDto);
  }

  private DevicesApi devicesApi;

  public Devices(String baseUrl) {
    super(baseUrl);

    devicesApi = restAdapter.create(DevicesApi.class);
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier) {
    return devicesApi.create(BEARER_TOKEN, new RegisteredDeviceDto(identifier));
  }

  public Observable<RegisteredDeviceDto> create(final String channel, final String identifier, final String name) {
    return devicesApi.create(BEARER_TOKEN, new RegisteredDeviceDto(identifier, name));
  }

  public Observable<RegisteredDeviceDto> activate(final String channel, final String identifier, String registrationId) {
    RegisteredDeviceDto deviceDto = new RegisteredDeviceDto();
    deviceDto.setIdentifier(identifier);
    deviceDto.setRegistrationId(registrationId);
    deviceDto.setProvider("gcm");

    return devicesApi.activate(BEARER_TOKEN, deviceDto);
  }
}