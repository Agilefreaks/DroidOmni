package com.omnipasteapp.omniapi;

import com.omnipasteapp.omniapi.resources.Clippings;
import com.omnipasteapp.omniapi.resources.IClippings;
import com.omnipasteapp.omniapi.resources.Resource;

import javax.inject.Inject;

public class OmniApi implements IOmniApi {
  private static String BaseUrl;
  private static String ApiKey;

  private static final String Version = "v1";

  @Inject
  public OmniApi() {
  }

  public static void setBaseUrl(String baseUrl) {
    BaseUrl = baseUrl;
  }

  public static void setApiKey(String apiKey) {
    ApiKey = apiKey;
  }

  @Override
  public IClippings clippings() {
    return Resource.build(Clippings.class, BaseUrl, Version, ApiKey);
  }
}
