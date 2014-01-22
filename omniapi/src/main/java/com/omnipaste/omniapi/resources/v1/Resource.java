package com.omnipaste.omniapi.resources.v1;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public abstract class Resource {
  public static final String CONTENT_TYPE = "CONTENT_TYPE: application/json";
  public static final String ACCEPT = "ACCEPT: application/json";
  public static final String USER_AGENT = "User-Agent: OmniApi";

  protected final RestAdapter restAdapter;

  protected Resource(String baseUrl) {
    restAdapter = getBuilder(baseUrl).build();
  }

  protected RestAdapter.Builder getBuilder(String baseUrl) {
    return new RestAdapter.Builder()
        .setServer(baseUrl)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(getGsonBuilder().create()));
  }

  protected GsonBuilder getGsonBuilder() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  }
}
