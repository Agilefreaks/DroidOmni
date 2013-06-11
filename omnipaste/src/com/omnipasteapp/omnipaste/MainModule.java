package com.omnipasteapp.omnipaste;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(IConfigurationProvider.class).to(SharedPreferencesConfigurationProvider.class).in(Singleton.class);
  }
}
