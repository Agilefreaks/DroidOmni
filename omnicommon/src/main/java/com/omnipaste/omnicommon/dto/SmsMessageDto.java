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

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getPhoneNumberList() {
    return phoneNumberList;
  }

  public void setPhoneNumberList(List<String> phoneNumberList) {
    this.phoneNumberList = phoneNumberList;
  }

  public List<String> getContentList() {
    return contentList;
  }

  public void setContentList(List<String> contentList) {
    this.contentList = contentList;
  }
}
