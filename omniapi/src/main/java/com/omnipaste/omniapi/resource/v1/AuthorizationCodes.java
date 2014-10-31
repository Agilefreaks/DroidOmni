package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

public class AuthorizationCodes extends AuthorizationResource<AuthorizationCodes.AuthorizationCodesApi> {
  protected interface AuthorizationCodesApi {
    @GET("/v1/authorization_codes.json")
    Observable<AuthorizationCodeDto> get(@Header("Authorization") String token, @Query("emails[]") String[] emails);
  }

  @Inject
  public AuthorizationCodes(RestAdapter restAdapter) {
    super(restAdapter, AuthorizationCodesApi.class);
  }

  public Observable<AuthorizationCodeDto> get(String token, String[] emails) {
    return api.get(bearerToken(token), emails);
  }
}
