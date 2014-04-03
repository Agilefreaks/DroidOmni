package com.omnipaste.omniapi;

import retrofit.RequestInterceptor;

public class OmniRequestInterceptor implements RequestInterceptor {
  @Override
  public void intercept(RequestFacade request) {
    request.addHeader("CONTENT_TYPE", "application/json");
    request.addHeader("ACCEPT", "application/json");
    request.addHeader("User-Agent", "Android OmniApi");
    request.addHeader("Connection", "Close");
  }
}
