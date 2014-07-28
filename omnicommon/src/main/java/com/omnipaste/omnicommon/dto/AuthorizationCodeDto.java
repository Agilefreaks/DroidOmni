package com.omnipaste.omnicommon.dto;

public class AuthorizationCodeDto {
  private String code;

  public AuthorizationCodeDto(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
