package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class IncomingCallNotificationDto {
  private String phoneNumber;

  public IncomingCallNotificationDto(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}