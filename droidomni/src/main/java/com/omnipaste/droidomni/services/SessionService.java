package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

public interface SessionService {
  Boolean login();

  void login(AccessTokenDto accessTokenDto);

  void logout();
}
