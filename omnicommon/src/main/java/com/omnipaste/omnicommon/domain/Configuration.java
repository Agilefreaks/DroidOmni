package com.omnipaste.omnicommon.domain;

import com.omnipaste.omnicommon.dto.AccessTokenDto;

public class Configuration {
  private String gcmSenderId, apiUrl, apiClientId;
  private AccessTokenDto accessToken;

  public Configuration() {
  }

  public String getGcmSenderId() {
    return gcmSenderId;
  }

  public void setGcmSenderId(String value) {
    gcmSenderId = value;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String value) {
    apiUrl = value;
  }

  public void setApiClientId(String apiClientId) {
    this.apiClientId = apiClientId;
  }

  public String getApiClientId() {
    return apiClientId;
  }

  public void setAccessToken(AccessTokenDto accessToken) {
    this.accessToken = accessToken;
  }

  public AccessTokenDto getAccessToken() {
    return accessToken;
  }
}
