package com.omnipaste.omnicommon.dto;

import java.util.Date;

public class RegisteredDeviceDto {
  public String identifier;
  public String name;
  public String registration_id;
  public Date updated_at;
  public Date created_at;

  public RegisteredDeviceDto(String identifier) {
    this.identifier = identifier;
  }

  public RegisteredDeviceDto(String identifier, String name) {
    this(identifier);

    this.name = name;
  }
}
