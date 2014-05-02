package com.omnipaste.omniapi.resources.v1;

import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.AuthorizationObservable;
import com.omnipaste.omniapi.deserializers.TelephonyNotificationTypeDeserializer;
import com.omnipaste.omniapi.serializers.TelephonyNotificationTypeSerializer;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.dto.TelephonyNotificationDto;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public class Notifications extends Resource {
  private interface NotificationsApi {
    @POST("/v1/notifications.json")
    Observable<TelephonyNotificationDto> create(@Header("Authorization") String token, @Body TelephonyNotificationDto telephonyNotificationDto);
  }

  private NotificationsApi notificationsApi;

  public Notifications(AuthorizationObservable authorizationObservable, AccessTokenDto accessToken, String baseUrl) {
    super(authorizationObservable, accessToken, baseUrl);

    notificationsApi = restAdapter.create(NotificationsApi.class);
  }

  public Observable<TelephonyNotificationDto> create(TelephonyNotificationDto telephonyNotificationDto) {
    return authorizationObservable.authorize(notificationsApi.create(bearerToken(accessToken), telephonyNotificationDto));
  }

  @Override
  protected GsonBuilder getGsonBuilder() {
    return super
        .getGsonBuilder()
        .registerTypeAdapter(TelephonyNotificationDto.TelephonyNotificationType.class, new TelephonyNotificationTypeDeserializer())
        .registerTypeAdapter(TelephonyNotificationDto.TelephonyNotificationType.class, new TelephonyNotificationTypeSerializer());
  }
}
