package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class LocalConfigurationService implements ConfigurationService {
  private Configuration configuration;

  public LocalConfigurationService() {
  }

  @Override
  public Configuration getConfiguration() {
    return configuration != null ? configuration : new Configuration();
  }

  @Override
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
}
