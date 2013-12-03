package com.omnipasteapp.omnimessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniMessagingModule {
  @Provides
  @Singleton
  IMessagingService provideMessagingService(GoogleMessagingService googleMessagingService) {
    return googleMessagingService;
  }
}
