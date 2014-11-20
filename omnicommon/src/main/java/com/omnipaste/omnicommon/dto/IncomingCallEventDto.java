package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class IncomingCallEventDto {
  private String phoneNumber;
  private String contactName;

  public IncomingCallEventDto() {
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public IncomingCallEventDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public IncomingCallEventDto setContactName(String contactName) {
    this.contactName = contactName;
    return this;
  }

  public String getContactName() {
    return contactName;
  }
}
