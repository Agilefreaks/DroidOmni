package com.omnipaste.omnicommon.dto;

public class RegisteredDeviceDto {
  public String identifier;
  public String name;
  public String registration_id;
  public String provider;

  public RegisteredDeviceDto() {
  }

  public RegisteredDeviceDto(String identifier) {
    this.identifier = identifier;
  }

  public RegisteredDeviceDto(String identifier, String name) {
    this(identifier);

    this.name = name;
  }
}
