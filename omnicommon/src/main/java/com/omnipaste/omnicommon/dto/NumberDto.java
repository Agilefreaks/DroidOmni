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

  public void setNumber(String number) {
    this.number = number;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof NumberDto)) {
      return false;
    }

    NumberDto other = (NumberDto) o;

    return this.getNumber().equals(other.getNumber()) &&
      this.getType().equals(other.getType());
  }
}

