package com.omnipaste.omniapi;

import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniApiModule {
  @Singleton
  @Provides
  public IOmniApi providesOmniApi(ConfigurationService configurationService) {
    return new OmniApi(configurationService.getConfiguration().getApiUrl());
  }
}