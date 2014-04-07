package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class AccessTokenDto {
  private String accessToken;
  private String refreshToken;

  public AccessTokenDto() {
  }

  public AccessTokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
