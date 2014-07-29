package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

import rx.functions.Action1;

public interface SessionService {
  Boolean login();

  void login(AccessTokenDto accessTokenDto);

  void login(String code, final Action1<AccessTokenDto> onSuccess, final Action1<Throwable> onError);

  void logout();
}
