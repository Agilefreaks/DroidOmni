package com.omnipasteapp.omniapi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniapiModule {
  @Provides
  @Singleton
  IOmniApi provideOmniApi(OmniApi omniApi) {
    return omniApi;
  }
}
