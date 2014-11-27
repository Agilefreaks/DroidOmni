package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.RegisteredDeviceDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import rx.Observable;

@Singleton
public class Devices extends AuthorizationResource<Devices.DevicesApi> {
  protected interface DevicesApi {
    @GET("/v1/devices.json")
    Observable<RegisteredDeviceDto[]> get(@Header("Authorization") String token);

    @POST("/v1/devices.json")
    Observable<RegisteredDeviceDto> create(@Header("Authorization") String token, @Body RegisteredDeviceDto deviceDto);

    @PUT("/v1/devices/activate.json")
    Observable<RegisteredDeviceDto> activate(@Header("Authorization") String token, @Body RegisteredDeviceDto deviceDto);

    @FormUrlEncoded
    @PUT("/v1/devices/deactivate.json")
    Observable<RegisteredDeviceDto> deactivate(@Header("Authorization") String token, @Field("identifier") String identifier);
  }

  @Inject
  public Devices(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, DevicesApi.class, authorizationService);
  }

  public Observable<RegisteredDeviceDto[]> get() {
    return authorizationService.authorize(api.get(bearerAccessToken()));
  }

  public Observable<RegisteredDeviceDto> create(final String identifier) {
    return authorizationService.authorize(api.create(bearerAccessToken(), new RegisteredDeviceDto(identifier)));
  }

  public Observable<RegisteredDeviceDto> create(final String identifier, final String name) {
    return authorizationService.authorize(api.create(bearerAccessToken(), new RegisteredDeviceDto(identifier, name)));
  }

  public Observable<RegisteredDeviceDto> activate(final String identifier, String registrationId) {
    RegisteredDeviceDto deviceDto = new RegisteredDeviceDto();
    deviceDto.setIdentifier(identifier);
    deviceDto.setRegistrationId(registrationId);
    deviceDto.setProvider("gcm");

    return authorizationService.authorize(api.activate(bearerAccessToken(), deviceDto));
  }

  public Observable<RegisteredDeviceDto> deactivate(final String identifier) {
    return authorizationService.authorize(api.deactivate(bearerAccessToken(), identifier));
  }
}