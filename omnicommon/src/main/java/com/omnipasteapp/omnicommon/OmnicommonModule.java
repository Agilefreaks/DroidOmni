package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnicommon.messaging.GoogleMessagingService;
import com.omnipasteapp.omnicommon.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;
import com.omnipasteapp.omnicommon.services.OmniService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmnicommonModule {

  @Provides
  IOmniService provideOmniService(OmniService omniService) {
    return omniService;
  }

  @Provides
  @Singleton
  IConfigurationService provideConfigurationService(ConfigurationService service) {
    return service;
  }

  @Provides
  @Singleton
  IMessagingService provideMessagingService(GoogleMessagingService googleMessagingService) {
    return googleMessagingService;
  }
}
