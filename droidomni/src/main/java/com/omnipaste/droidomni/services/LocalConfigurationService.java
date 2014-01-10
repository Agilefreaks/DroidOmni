package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class LocalConfigurationService implements ConfigurationService {
  @Override
  public Configuration getConfiguration() {
    return new Configuration("calinuswork@gmail.com");
  }
}
