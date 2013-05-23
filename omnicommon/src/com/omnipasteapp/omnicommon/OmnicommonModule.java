package com.omnipasteapp.omnicommon;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnicommon.services.OmniService;

public class OmnicommonModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(IOmniService.class).to(OmniService.class).in(Singleton.class);
  }
}
