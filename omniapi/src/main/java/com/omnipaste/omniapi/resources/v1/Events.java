package com.omnipaste.omniapi.resources.v1;

import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.AuthorizationObservable;
import com.omnipaste.omniapi.deserializers.TelephonyNotificationTypeDeserializer;
import com.omnipaste.omniapi.serializers.TelephonyNotificationTypeSerializer;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public class Events extends Resource {
  private interface NotificationsApi {
    @POST("/v1/events.json")
    Observable<TelephonyEventDto> create(@Header("Authorization") String token, @Body TelephonyEventDto telephonyEventDto);
  }

  private final NotificationsApi notificationsApi;

  public Events(AuthorizationObservable authorizationObservable, AccessTokenDto accessToken, String baseUrl) {
    super(authorizationObservable, accessToken, baseUrl);

    notificationsApi = restAdapter.create(NotificationsApi.class);
  }

  public Observable<TelephonyEventDto> create(TelephonyEventDto telephonyEventDto) {
    return authorizationObservable.authorize(notificationsApi.create(bearerToken(accessToken), telephonyEventDto));
  }

  @Override
  protected GsonBuilder getGsonBuilder() {
    return super
        .getGsonBuilder()
        .registerTypeAdapter(TelephonyEventDto.TelephonyEventType.class, new TelephonyNotificationTypeDeserializer())
        .registerTypeAdapter(TelephonyEventDto.TelephonyEventType.class, new TelephonyNotificationTypeSerializer());
  }
}
