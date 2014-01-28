package com.omnipaste.phoneprovider;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class PhoneProviderModule {
  @Provides
  public IPhoneProvider providesPhoneProvider(PhoneProvider phoneProvider) {
    return phoneProvider;
  }
}
