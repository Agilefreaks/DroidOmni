package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class IncomingCallEventDto {
  private String phoneNumber;

  public IncomingCallEventDto(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
