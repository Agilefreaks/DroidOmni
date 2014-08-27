package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class IncomingSmsEventDto {
  private String phoneNumber;
  private String content;

  public IncomingSmsEventDto() {
  }

  public IncomingSmsEventDto(String phoneNumber, String content) {
    this.phoneNumber = phoneNumber;
    this.content = content;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public IncomingSmsEventDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public String getContent() {
    return content;
  }

  public IncomingSmsEventDto setContent(String content) {
    this.content = content;
    return this;
  }
}