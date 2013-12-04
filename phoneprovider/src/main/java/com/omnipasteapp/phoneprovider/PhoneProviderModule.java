package com.omnipasteapp.phoneprovider;

import com.omnipasteapp.omnicommon.interfaces.IPhoneProvider;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class PhoneProviderModule {
  @Provides
  IPhoneProvider providePhoneProvider(PhoneProvider phoneProvider) {
    return phoneProvider;
  }
}
