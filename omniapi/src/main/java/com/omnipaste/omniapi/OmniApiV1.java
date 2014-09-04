package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.AuthorizationCodes;
import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Events;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.domain.Configuration;
import com.omnipaste.omnicommon.dto.AccessTokenDto;
import com.omnipaste.omnicommon.services.ConfigurationService;

public class OmniApiV1 implements OmniApi {
  private String apiClientId;
  private final String baseUrl;
  private Devices devices;
  private Clippings clippings;
  private Events events;
  private AuthorizationCodes authorizationCodes;
  private Token token;
  private AccessTokenDto accessToken;
  private ConfigurationService configurationService;

  public OmniApiV1(ConfigurationService configurationService) {
    Configuration configuration = configurationService.getConfiguration();

    this.apiClientId = configuration.getApiClientId();
    this.baseUrl = configuration.getApiUrl();
    this.configurationService = configurationService;
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

  public Events events() {
    ensureAccessToken();
    return events == null
        ? events = new Events(new AuthorizationObservable(token(), accessToken), accessToken, baseUrl)
        : events;
  }

  @Override
  public AuthorizationCodes authorizationCodes() {
    return authorizationCodes == null
        ? authorizationCodes = new AuthorizationCodes(baseUrl)
        : authorizationCodes;
  }

  @Override
  public Token token() {
    return token == null ? token = new Token(apiClientId, baseUrl) : token;
  }

  private void ensureAccessToken() {
    AccessTokenDto configurationAccessToken = configurationService.getConfiguration().getAccessToken();

    if (accessToken != configurationAccessToken) {
      setAccessToken(configurationAccessToken);
    }

    if (accessToken == null) {
      throw new IllegalArgumentException("You need to set the Access Token on the API before using this endpoint.");
    }
  }

  private void setAccessToken(AccessTokenDto accessToken) {
    this.accessToken = accessToken;
    devices = null;
    clippings = null;
    authorizationCodes = null;
    events = null;
  }
}
