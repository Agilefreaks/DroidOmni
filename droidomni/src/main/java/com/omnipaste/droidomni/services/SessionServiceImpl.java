package com.omnipaste.droidomni.services;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.droidomni.events.LoginEvent;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
  public void login(String code, final Action1<AccessTokenDto> onSuccess, Action1<Throwable> onError) {
    omniApi.token().create(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            // OnNext
            new Action1<AccessTokenDto>() {
              @Override
              public void call(AccessTokenDto accessTokenDto) {
                login(accessTokenDto);
                onSuccess.call(accessTokenDto);
              }
            },
            onError);
  }

  @Override
  public void logout() {
    Configuration configuration = configurationService.getConfiguration();
    configuration.setAccessToken(null);
    configurationService.setConfiguration(configuration);
  }
}
