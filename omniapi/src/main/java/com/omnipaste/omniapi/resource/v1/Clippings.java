package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.ClippingDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

@Singleton
public class Clippings extends AuthorizationResource<Clippings.ClippingsApi> {
  protected interface ClippingsApi {
    @GET("/v1/clippings/{id}.json")
    Observable<ClippingDto> get(@Header("Authorization") String token, @Path("id") String id);

    @POST("/v1/clippings.json")
    Observable<ClippingDto> create(@Header("Authorization") String token, @Body ClippingDto content);
  }

  @Inject
  public Clippings(AuthorizationService authorizationService, RestAdapter restAdapter) {
    super(restAdapter, ClippingsApi.class, authorizationService);
  }

  public Observable<ClippingDto> get(String id) {
    return authorizationService.authorize(api.get(bearerAccessToken(), id));
  }

  public Observable<ClippingDto> create(ClippingDto clippingDto) {
    return authorizationService.authorize(api.create(bearerAccessToken(), clippingDto));
  }
}
