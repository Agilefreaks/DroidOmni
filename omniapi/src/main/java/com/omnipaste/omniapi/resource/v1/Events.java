package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.EventDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

@Singleton
public class Events extends AuthorizationResource<Events.NotificationsApi> {
  protected interface NotificationsApi {
    @POST("/v1/events.json")
    Observable<TelephonyEventDto> create(@Header("Authorization") String token, @Body TelephonyEventDto telephonyEventDto);

    @GET("/v1/events.json")
    Observable<EventDto> get(@Header("Authorization") String token);
  }

  @Inject
  public Events(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, NotificationsApi.class, authorizationService);
  }

  public Observable<TelephonyEventDto> create(TelephonyEventDto telephonyEventDto) {
    return authorizationService.authorize(api.create(bearerAccessToken(), telephonyEventDto));
  }

  public Observable<EventDto> get() {
    return authorizationService.authorize(api.get(bearerAccessToken()));
  }
}
