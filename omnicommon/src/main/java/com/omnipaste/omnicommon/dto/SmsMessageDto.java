package com.omnipaste.omnicommon.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class SmsMessageDto {
  public String phoneNumber = "";
  public String content = "";
  public List<String> phoneNumberList = new ArrayList<>();
  public List<String> contentList = new ArrayList<>();

  public SmsMessageDto() {
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public SmsMessageDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public String getContent() {
    return content;
  }

  public SmsMessageDto setContent(String content) {
    this.content = content;
    return this;
  }

  public List<String> getPhoneNumberList() {
    return phoneNumberList;
  }

  public SmsMessageDto setPhoneNumberList(List<String> phoneNumberList) {
    this.phoneNumberList = phoneNumberList;
    return this;
  }

  public List<String> getContentList() {
    return contentList;
  }

  public SmsMessageDto setContentList(List<String> contentList) {
    this.contentList = contentList;
    return this;
  }
}
