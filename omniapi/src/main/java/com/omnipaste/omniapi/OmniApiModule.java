package com.omnipaste.omniapi;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniApiModule {
  @Singleton
  @Provides
  public OmniApi providesOmniApi(ConfigurationService configurationService) {
    Configuration configuration = configurationService.getConfiguration();
    return new OmniApiV1(configuration.getApiClientId(), configuration.getApiUrl());
  }
}
