package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class NumberDto {
  public String number;
  public String type;

  public NumberDto(String number, String type) {
    this.number = number;
    this.type = type;
  }

  public String getNumber() {
    return number;
  }

  public String getType() {
    return type;
  }
}

