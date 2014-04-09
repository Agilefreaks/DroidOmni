package com.omnipaste.droidomni.events;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

public class LoginEvent {
  private AccessTokenDto accessTokenDto;

  public LoginEvent(AccessTokenDto accessTokenDto) {
    this.accessTokenDto = accessTokenDto;
  }

  public AccessTokenDto getAccessToken() {
    return accessTokenDto;
  }
}
