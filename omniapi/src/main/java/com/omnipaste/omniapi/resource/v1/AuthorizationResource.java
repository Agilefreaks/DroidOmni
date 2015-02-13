package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import retrofit.RestAdapter;

public abstract class AuthorizationResource<T> extends Resource<T> {
  protected AuthorizationService authorizationService;

  protected AuthorizationResource(RestAdapter restAdapter, Class<T> apiService) {
    super(restAdapter, apiService);
  }

  protected AuthorizationResource(RestAdapter restAdapter, Class<T> apiService, AuthorizationService authorizationService) {
    super(restAdapter, apiService);
    this.authorizationService = authorizationService;
  }

  public String bearerAccessToken() {
    return bearerToken(authorizationService.getAccessTokenDto());
  }

  protected String bearerToken(AccessTokenDto accessTokenDto) {
    return "bearer ".concat(accessTokenDto.getAccessToken());
  }

  protected String bearerToken(String accessToken) {
    return "bearer ".concat(accessToken);
  }
}
