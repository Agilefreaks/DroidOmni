package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

public class SessionServiceImpl implements SessionService {

  private ConfigurationService configurationService;

  @Inject
  public SessionServiceImpl(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  @Override
  public void login(String channel) {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setChannel(channel);
    configurationService.setConfiguration(configuration);
  }

  @Override
  public void logout() {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setChannel(null);
    configurationService.setConfiguration(configuration);
  }

  @Override
  public Boolean isLogged() {
    return configurationService.getConfiguration().hasChannel();
  }

  @Override
  public String getChannel() {
    return configurationService.getConfiguration().getChannel();
  }
}
