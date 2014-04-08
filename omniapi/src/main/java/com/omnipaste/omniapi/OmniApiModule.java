package com.omnipaste.omniapi;

import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniApiModule {
  @Singleton
  @Provides
  public OmniApi providesOmniApi(ConfigurationService configurationService) {
    return new OmniApiV1(configurationService.getConfiguration().getApiUrl());
  }
}
