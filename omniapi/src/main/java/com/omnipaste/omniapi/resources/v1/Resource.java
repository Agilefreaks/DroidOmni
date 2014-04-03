package com.omnipaste.omniapi.resources.v1;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.OmniRequestInterceptor;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public abstract class Resource {
  public static String BEARER_TOKEN = "bearer 5uLiz2kTAZkInyM8k1zYG1pDB6jYpJAjNuFQaO8H7t7Fy76qI1fKLi9uKjEDyyqC8Zm3JLWr5SwQ41smyiLY7w==";

  protected final RestAdapter restAdapter;

  protected Resource(String baseUrl) {
    restAdapter = getBuilder(baseUrl).build();
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
}
