package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmnicommonModule {
  @Provides
  @Singleton
  IConfigurationService provideConfigurationService(ConfigurationService service) {
    return service;
  }
}
