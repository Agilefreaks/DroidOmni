package com.omnipasteapp.omnicommon.models;

public class Device {
  private String registrationId;

  public Device(String registrationId) {
    this.registrationId = registrationId;
  }

  public String getRegistrationId() {
    return registrationId;
  }
}
