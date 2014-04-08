package com.omnipaste.phoneprovider;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class PhoneProviderModule {
  @Provides
  public PhoneProvider providesPhoneProvider(AndroidPhoneProvider androidPhoneProvider) {
    return androidPhoneProvider;
  }
}
