package com.omnipaste.omniapi.resources.v1;

import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.deserializers.ClippingTypeDeserializer;
import com.omnipaste.omnicommon.dto.ClippingDto;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public class Clippings extends Resource {
  private interface ClippingsApi {
    @GET("/v1/clippings/last.json")
    Observable<ClippingDto> last(@Header("Authorization") String token);

    @POST("/v1/clippings.json")
    Observable<ClippingDto> create(@Header("Authorization") String token, @Body ClippingDto content);
  }

  private ClippingsApi clippingsApi;

  public Clippings(String baseUrl) {
    super(baseUrl);

    clippingsApi = restAdapter.create(ClippingsApi.class);
  }

  public Observable<ClippingDto> last(String channel) {
    return clippingsApi.last(BEARER_TOKEN);
  }

  public Observable<ClippingDto> create(String channel, ClippingDto clippingDto) {
    return clippingsApi.create(BEARER_TOKEN, clippingDto);
  }

  @Override
  protected GsonBuilder getGsonBuilder() {
    return super.getGsonBuilder().registerTypeAdapter(ClippingDto.ClippingType.class, new ClippingTypeDeserializer());
  }
}
