package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

public class SessionServiceImpl implements SessionService {

  private ConfigurationService configurationService;

  @Inject
  public SessionServiceImpl(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  @Override
  public void login(AccessTokenDto accessTokenDto) {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setAccessToken(accessTokenDto);
    configurationService.setConfiguration(configuration);
  }

  @Override
  public void logout() {
    Configuration configuration = configurationService.getConfiguration();
    // configuration.setChannel(null);
    configurationService.setConfiguration(configuration);
  }

  @Override
  public Boolean isLogged() {
    return false; //configurationService.getConfiguration().hasChannel();
  }
}
