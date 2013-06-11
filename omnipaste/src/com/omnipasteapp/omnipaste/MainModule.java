package com.omnipasteapp.omnipaste;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnipaste.providers.SharedPreferencesConfigurationProvider;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.omnipasteapp.omnipaste.services.IntentService;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(IConfigurationProvider.class).to(SharedPreferencesConfigurationProvider.class).in(Singleton.class);
    bind(IIntentService.class).to(IntentService.class);
  }
}
