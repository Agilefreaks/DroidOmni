package com.omnipaste.omniapi;

import com.omnipaste.omniapi.prefs.PrefsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    includes = PrefsModule.class,
    complete = false,
    library = true
)
public class OmniApiModule {
  @Provides @Singleton
  public OmniApi providesOmniApi() {
    return new OmniApiV1(null);
  }
}
