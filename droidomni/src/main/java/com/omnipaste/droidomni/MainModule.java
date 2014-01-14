package com.omnipaste.droidomni;

import com.omnipaste.droidomni.activities.MainActivity_;
import com.omnipaste.droidomni.fragments.LoginFragment_;
import com.omnipaste.droidomni.services.LocalConfigurationService;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        MainActivity_.class,
        LoginFragment_.class
    }
)
public class MainModule {
  @Singleton
  @Provides
  public ConfigurationService providesConfigurationService() {
    return new LocalConfigurationService();
  }
}
