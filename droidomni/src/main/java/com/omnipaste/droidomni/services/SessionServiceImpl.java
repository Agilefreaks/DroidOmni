package com.omnipaste.droidomni.services;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

public class SessionServiceImpl implements SessionService {

  private ConfigurationService configurationService;

  @Inject
  OmniApi omniApi;

  @Inject
  public SessionServiceImpl(ConfigurationService configurationService) {
    DroidOmniApplication.inject(this);

    this.configurationService = configurationService;
  }

  @Override
  public Boolean login() {
    Configuration configuration = configurationService.getConfiguration();
    AccessTokenDto accessToken = configuration.getAccessToken();

    if (accessToken != null) {
      login(accessToken);
    }

    return accessToken != null;
  }

  @Override
  public void login(AccessTokenDto accessTokenDto) {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setAccessToken(accessTokenDto);
    configurationService.setConfiguration(configuration);

    omniApi.setAccessToken(accessTokenDto);
  }

  @Override
  public void logout() {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setAccessToken(null);
    configurationService.setConfiguration(configuration);
  }
}
