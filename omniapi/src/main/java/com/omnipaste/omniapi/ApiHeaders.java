package com.omnipaste.omniapi;

import javax.inject.Inject;

import retrofit.RequestInterceptor;

public class ApiHeaders implements RequestInterceptor {
  @Inject
  public ApiHeaders() {
  }

  @Override
  public void intercept(RequestFacade request) {
    request.addHeader("CONTENT_TYPE", "application/json");
    request.addHeader("ACCEPT", "application/json");
    request.addHeader("User-Agent", "Android OmniApi");
    request.addHeader("Connection", "Close");
  }
}
