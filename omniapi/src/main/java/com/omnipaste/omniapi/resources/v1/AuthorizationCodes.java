package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.AuthorizationCodeDto;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;
import rx.Observable;

public class AuthorizationCodes extends Resource {
  private interface AuthorizationCodesApi {
    @GET("/v1/authorization_codes.json")
    Observable<AuthorizationCodeDto> get(@Header("Authorization") String token, @Query("emails[]") String[] emails);
  }

  private final AuthorizationCodesApi authorizationCodesApi;

  public AuthorizationCodes(String baseUrl) {
    super(baseUrl);

    authorizationCodesApi = restAdapter.create(AuthorizationCodesApi.class);
  }

  public Observable<AuthorizationCodeDto> get(String token, String[] emails) {
    return authorizationCodesApi.get(bearerToken(token), emails);
  }
}
