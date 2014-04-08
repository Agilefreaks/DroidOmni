package com.omnipaste.droidomni.services;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import rx.Observable;

public class LoginService {
  @Inject
  public OmniApi omniApi;

  @Inject
  public ConfigurationService configurationService;

  public LoginService() {
    DroidOmniApplication.inject(this);
  }

  public Observable<AccessTokenDto> login(String code) {
    return omniApi.token().create(getConfiguration().getApiClientId(), code);
  }

  private Configuration getConfiguration() {
    return configurationService.getConfiguration();
  }
}
