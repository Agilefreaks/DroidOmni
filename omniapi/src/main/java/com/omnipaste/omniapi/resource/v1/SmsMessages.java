package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;

@Singleton
public class SmsMessages extends AuthorizationResource<SmsMessages.SmsMessagesApi> {
  protected interface SmsMessagesApi {
    @GET("/v1/sms_messages/{id}.json")
    Observable<SmsMessageDto> get(@Header("Authorization") String token, @Path("id") String id);
  }

  @Inject
  protected SmsMessages(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, SmsMessagesApi.class, authorizationService);
  }

  public Observable<SmsMessageDto> get(String id) {
    return authorizationService.authorize(api.get(bearerAccessToken(), id));
  }
}
