package com.omnipaste.omniapi.resource.v1;

import retrofit.RestAdapter;

public abstract class Resource<T> {
  protected final T api;

  protected Resource(RestAdapter restAdapter, Class<T> apiService) {
    api = restAdapter.create(apiService);
  }
}
