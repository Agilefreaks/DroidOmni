package com.omnipaste.omniapi.resources.v1;

import com.omnipaste.omnicommon.dto.ClippingDto;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;
import rx.concurrency.Schedulers;

public class Clippings extends Resource {
  private interface ClippingsApi {
    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT})
    @GET("/v1/clippings/last.json")
    Observable<ClippingDto> last(@Header("CHANNEL") String channel);

    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT})
    @POST("/v1/clippings.json")
    Observable<ClippingDto> create(@Header("CHANNEL") String channel, @Body ClippingDto content);
  }

  private ClippingsApi clippingsApi;

  public Clippings(String baseUrl) {
    super(baseUrl);

    clippingsApi = builder.build().create(ClippingsApi.class);
  }

  public Observable<ClippingDto> last(String channel) {
    return clippingsApi.last(channel).subscribeOn(Schedulers.threadPoolForIO());
  }

  public Observable<ClippingDto> create(String channel, String content) {
    ClippingDto clippingDto = new ClippingDto();
    clippingDto.setContent(content);

    return clippingsApi.create(channel, clippingDto).subscribeOn(Schedulers.threadPoolForIO());
  }
}
