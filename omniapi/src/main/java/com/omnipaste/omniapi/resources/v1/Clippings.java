package com.omnipaste.omniapi.resources.v1;

import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.deserializers.ClippingTypeDeserializer;
import com.omnipaste.omnicommon.dto.ClippingDto;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;
import rx.schedulers.Schedulers;

public class Clippings extends Resource {
  private interface ClippingsApi {
    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT, Resource.CONNECTION})
    @GET("/v1/clippings/last.json")
    Observable<ClippingDto> last(@Header("CHANNEL") String channel);

    @Headers({Resource.CONTENT_TYPE, Resource.ACCEPT, Resource.USER_AGENT, Resource.CONNECTION})
    @POST("/v1/clippings.json")
    Observable<ClippingDto> create(@Header("CHANNEL") String channel, @Body ClippingDto content);
  }

  private ClippingsApi clippingsApi;

  public Clippings(String baseUrl) {
    super(baseUrl);

    clippingsApi = restAdapter.create(ClippingsApi.class);
  }

  public Observable<ClippingDto> last(String channel) {
    return clippingsApi.last(channel).subscribeOn(Schedulers.io());
  }

  public Observable<ClippingDto> create(String channel, ClippingDto clippingDto) {
    return clippingsApi.create(channel, clippingDto).subscribeOn(Schedulers.io());
  }

  @Override
  protected GsonBuilder getGsonBuilder() {
    return super.getGsonBuilder().registerTypeAdapter(ClippingDto.ClippingType.class, new ClippingTypeDeserializer());
  }
}
