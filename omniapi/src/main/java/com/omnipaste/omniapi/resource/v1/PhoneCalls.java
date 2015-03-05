package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

@Singleton
public class PhoneCalls extends AuthorizationResource<PhoneCalls.PhoneCallsApi> {
  public interface PhoneCallsApi {
    @POST("/v1/phone_calls")
    public Observable<PhoneCallDto> create(@Header("Authorization") String token, @Body PhoneCallDto phoneCallDto);

    @GET("/v1/phone_calls/{id}.json")
    public Observable<PhoneCallDto> get(@Header("Authorization") String token, @Path("id") String id);

    @PATCH("/v1/phone_calls/{id}.json")
    public Observable<PhoneCallDto> patch(@Header("Authorization") String token, @Path("id") String id, @Body PhoneCallDto phoneCallDto);
  }

  @Inject
  public PhoneCalls(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, PhoneCallsApi.class, authorizationService);
  }

  public Observable<PhoneCallDto> create(PhoneCallDto phoneCallDto) {
    return authorizationService.authorize(api.create(bearerAccessToken(), phoneCallDto));
  }

  public Observable<PhoneCallDto> get(String id) {
    return authorizationService.authorize(api.get(bearerAccessToken(), id));
  }

  public Observable<PhoneCallDto> markAsStarted(String deviceId, String id) {
    return authorizationService.authorize(api.patch(bearerAccessToken(), id, new PhoneCallDto(deviceId).setType(PhoneCallDto.Type.OUTGOING).setState(PhoneCallDto.State.STARTED)));
  }

  public Observable<PhoneCallDto> markAsEnded(String deviceId, String id) {
    return authorizationService.authorize(api.patch(bearerAccessToken(), id, new PhoneCallDto(deviceId).setType(PhoneCallDto.Type.INCOMING).setState(PhoneCallDto.State.ENDED)));
  }
}
