package com.omnipaste.phoneprovider;

import com.omnipaste.phoneprovider.actions.Factory;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class PhoneProviderModule {
  @Provides
  public PhoneProvider providesPhoneProvider(AndroidPhoneProvider androidPhoneProvider) {
    return androidPhoneProvider;
  }

  @Provides
  public Factory providesFactory(ActionFactory actionFactory) {
    return actionFactory;
  }
}
