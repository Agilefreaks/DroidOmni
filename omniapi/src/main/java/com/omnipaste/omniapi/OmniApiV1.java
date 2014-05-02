package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Notifications;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

public class OmniApiV1 implements OmniApi {
  private String apiClientId;
  private final String baseUrl;
  private Devices devices;
  private Clippings clippings;
  private Notifications notifications;
  private Token token;
  private AccessTokenDto accessToken;

  public OmniApiV1(String apiClientId, String baseUrl) {
    this.apiClientId = apiClientId;
    this.baseUrl = baseUrl;
  }

  @Override
  public Devices devices() {
    ensureAccessToken();
    return devices == null
        ? devices = new Devices(new AuthorizationObservable(token(), accessToken), accessToken, baseUrl)
        : devices;
  }

  @Override
  public Clippings clippings() {
    ensureAccessToken();
    return clippings == null
        ? clippings = new Clippings(new AuthorizationObservable(token(), accessToken), accessToken, baseUrl)
        : clippings;
  }

  public Notifications notifications() {
    ensureAccessToken();
    return notifications == null
        ? notifications = new Notifications(new AuthorizationObservable(token(), accessToken), accessToken, baseUrl)
        : notifications;
  }

  @Override
  public Token token() {
    return token == null ? token = new Token(apiClientId, baseUrl) : token;
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
