package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class DeviceDto {
  private String id, name, registrationId, provider, publicKey;

  public DeviceDto() {
  }

  public DeviceDto(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public DeviceDto setRegistrationId(String registrationId) {
    this.registrationId = registrationId;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public DeviceDto setPublicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  public String getPublicKey() {
    return publicKey;
  }
}
