package com.omnipaste.droidomni.services;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.omniapi.IOmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import rx.Observable;

public class LoginService {
  private final Configuration configuration;

  @Inject
  public IOmniApi omniApi;

  @Inject
  public ConfigurationService configurationService;

  public LoginService() {
    DroidOmniApplication.inject(this);

    configuration = configurationService.getConfiguration();
  }

  public Observable<AccessTokenDto> login(String code) {
    return omniApi.token().create("1", code);
  }
}
