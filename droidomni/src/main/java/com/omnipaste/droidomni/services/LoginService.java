package com.omnipaste.droidomni.services;

import com.omnipaste.droidomni.DroidOmniApplication;
import com.omnipaste.omniapi.OmniApi;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import javax.inject.Inject;

import rx.Observable;

public class LoginService {
  @Inject
  public OmniApi omniApi;

  public LoginService() {
    DroidOmniApplication.inject(this);
  }

  public Observable<AccessTokenDto> login(String code) {
    return omniApi.token().create(code);
  }
}
