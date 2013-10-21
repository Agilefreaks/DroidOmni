package com.omnipasteapp.omniclipboard.api;

import com.omnipasteapp.omniclipboard.api.resources.Clippings;
import com.omnipasteapp.omniclipboard.api.resources.IClippings;

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
    return new Clippings(BaseUrl, Version, ApiKey);
  }
}
