package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class IncomingCallEventDto {
  private String phoneNumber;

  public IncomingCallEventDto() {
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public IncomingCallEventDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }
}
