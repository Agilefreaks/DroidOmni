package com.omnipaste.omniapi.resource.v1.user;

import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.UserDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Header;
import rx.Observable;

@Singleton
public class User extends AuthorizationResource<User.UserApi> {
  protected interface UserApi {
    @GET("/v1/user")
    Observable<UserDto> get(@Header("Authorization") String token);
  }

  @Inject
  public User(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, UserApi.class, authorizationService);
  }

  public Observable<UserDto> get() {
    return authorizationService.authorize(api.get(bearerAccessToken()));
  }
}
