package com.omnipaste.phoneprovider;

import com.omnipaste.omnicommon.providers.NotificationProvider;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class PhoneProviderModule {
  @Provides
  public PhoneProvider providesPhoneProvider(NotificationProvider notificationProvider, ActionFactory actionFactory) {
    return new PhoneProvider(notificationProvider, actionFactory);
  }
}
