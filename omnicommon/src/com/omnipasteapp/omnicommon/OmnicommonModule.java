package com.omnipasteapp.omnicommon;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.framework.IOmniServiceFactory;
import com.omnipasteapp.omnicommon.framework.OmniServiceFactory;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;
import com.omnipasteapp.omnicommon.services.OmniService;

public class OmnicommonModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(IOmniService.class).to(OmniService.class);
    bind(IOmniServiceFactory.class).to(OmniServiceFactory.class).in(Singleton.class);
    bind(IConfigurationService.class).to(ConfigurationService.class).in(Singleton.class);
  }
}
