package com.omnipaste.droidomni.services;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

public interface SessionService {
  void login(AccessTokenDto accessTokenDto);

  void logout();

  Boolean isLogged();
}
