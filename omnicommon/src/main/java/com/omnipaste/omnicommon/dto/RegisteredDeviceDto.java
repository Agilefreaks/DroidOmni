package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class RegisteredDeviceDto {
  private String identifier, name, registrationId, provider;

  public RegisteredDeviceDto() {
  }

  public RegisteredDeviceDto(String identifier) {
    this.identifier = identifier;
  }

  public RegisteredDeviceDto(String identifier, String name) {
    this(identifier);

    this.name = name;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public void setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }
}
