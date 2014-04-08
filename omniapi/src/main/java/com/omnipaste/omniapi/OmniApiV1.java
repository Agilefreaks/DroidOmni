package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

class OmniApiV1 implements OmniApi {
  private final String baseUrl;
  private Devices devices;
  private Clippings clippings;
  private Token token;
  private AccessTokenDto accessToken;

  OmniApiV1(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public Devices devices() {
    ensureAccessToken();
    return devices == null ? devices = new Devices(accessToken, baseUrl) : devices;
  }

  @Override
  public Clippings clippings() {
    ensureAccessToken();
    return clippings == null ? clippings = new Clippings(accessToken, baseUrl) : clippings;
  }

  @Override
  public Token token() {
    return token == null ? token = new Token(baseUrl) : token;
  }

  @Override
  public void setAccessToken(AccessTokenDto accessToken) {
    this.accessToken = accessToken;
    devices = null;
    clippings = null;
  }

  private void ensureAccessToken() {
    if (accessToken == null) {
      throw new IllegalArgumentException("You need to set the Access Token on the API before using this endpoint.");
    }
  }
}
