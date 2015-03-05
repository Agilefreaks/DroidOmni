package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

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
public class SmsMessages extends AuthorizationResource<SmsMessages.SmsMessagesApi> {
  protected interface SmsMessagesApi {
    @GET("/v1/sms_messages/{id}.json")
    Observable<SmsMessageDto> get(@Header("Authorization") String token, @Path("id") String id);

    @POST("/v1/sms_messages")
    Observable<SmsMessageDto> post(@Header("Authorization") String token, @Body SmsMessageDto smsMessage);

    @PATCH("/v1/sms_messages/{id}.json")
    Observable<SmsMessageDto> patch(@Header("Authorization") String token, @Path("id") String id, @Body SmsMessageDto smsMessageDto);
  }

  @Inject
  protected SmsMessages(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, SmsMessagesApi.class, authorizationService);
  }

  public Observable<SmsMessageDto> get(String id) {
    return authorizationService.authorize(api.get(bearerAccessToken(), id));
  }

  public Observable<SmsMessageDto> post(SmsMessageDto smsMessage) {
    return authorizationService.authorize(api.post(bearerAccessToken(), smsMessage));
  }

  public Observable<SmsMessageDto> markAsSent(String deviceId, String id) {
    return authorizationService.authorize(api.patch(bearerAccessToken(), id, new SmsMessageDto(deviceId).setType(SmsMessageDto.Type.OUTGOING).setState(SmsMessageDto.State.SENT)));
  }
}
