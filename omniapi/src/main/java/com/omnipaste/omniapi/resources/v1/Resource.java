package com.omnipaste.omniapi.resources.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public abstract class Resource {
  public static final String CONTENT_TYPE = "CONTENT_TYPE: application/json";
  public static final String ACCEPT = "ACCEPT: application/json";
  public static final String USER_AGENT = "User-Agent: OmniApi";

  protected final RestAdapter.Builder builder;

  protected Resource(String baseUrl) {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    builder = new RestAdapter.Builder()
        .setServer(baseUrl)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(gson));
  }
}
