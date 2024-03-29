package com.omnipaste.omniapi.resource.v1.user;

import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.DeviceDto;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public class Devices extends AuthorizationResource<Devices.DevicesApi> {

  protected interface DevicesApi {
    @GET("/v1/user/devices.json")
    Observable<DeviceDto[]> get(@Header("Authorization") String token);

    @GET("/v1/user/devices/{id}.json")
    Observable<DeviceDto> get(@Header("Authorization") String token, @Path("id") String id);

    @POST("/v1/user/devices.json")
    Observable<DeviceDto> create(@Header("Authorization") String token, @Body DeviceDto deviceDto);

    @PATCH("/v1/user/devices/{id}.json")
    Observable<DeviceDto> patch(@Header("Authorization") String token, @Path("id") String id, @Body DeviceDto deviceDto);

    @DELETE("/v1/user/devices/{id}.json")
    Observable<Boolean> delete(@Header("Authorization") String token, @Path("id") String id);
  }

  @Inject
  protected Devices(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, DevicesApi.class, authorizationService);
  }

  public Observable<DeviceDto[]> get() {
    return authorizationService.authorize(api.get(bearerAccessToken()));
  }

  public Observable<DeviceDto> get(String id) {
    return authorizationService.authorize(api.get(bearerAccessToken(), id));
  }

  public Observable<DeviceDto> create(String name) {
    return authorizationService.authorize(api.create(bearerAccessToken(), new DeviceDto(name)));
  }

  public Observable<DeviceDto> activate(String id, String registrationId) {
    DeviceDto deviceDto = new DeviceDto()
      .setRegistrationId(registrationId)
      .setProvider("gcm");

    return authorizationService.authorize(api.patch(bearerAccessToken(), id, deviceDto));
  }

  public Observable<DeviceDto> deactivate(String id) {
    return authorizationService.authorize(api.patch(bearerAccessToken(), id, new DeviceDto().setRegistrationId("")));
  }

  public Observable<Boolean> delete(String id) {
    return authorizationService.authorize(api.delete(bearerAccessToken(), id));
  }
}
