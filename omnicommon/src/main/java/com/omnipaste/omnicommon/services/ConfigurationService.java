package com.omnipaste.omnicommon.services;

import com.omnipaste.omnicommon.domain.Configuration;

public interface ConfigurationService {
  public Configuration getConfiguration();

  public void setConfiguration(Configuration configuration);
}
