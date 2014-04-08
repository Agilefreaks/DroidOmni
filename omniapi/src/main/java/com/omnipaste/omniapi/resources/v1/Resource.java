package com.omnipaste.omniapi.resources.v1;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.OmniRequestInterceptor;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public abstract class Resource {
  protected final RestAdapter restAdapter;
  protected AccessTokenDto accessToken;

  protected Resource(String baseUrl) {
    restAdapter = getBuilder(baseUrl).build();
  }

  protected Resource(AccessTokenDto accessToken, String baseUrl) {
    this(baseUrl);
    this.accessToken = accessToken;
  }

  protected RestAdapter.Builder getBuilder(String baseUrl) {
    return new RestAdapter.Builder()
        .setEndpoint(baseUrl)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(getGsonBuilder().create()))
        .setRequestInterceptor(new OmniRequestInterceptor());
  }

  protected GsonBuilder getGsonBuilder() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  }

  protected String bearerToken(AccessTokenDto accessTokenDto) {
    return "bearer ".concat(accessTokenDto.getAccessToken());
  }
}
