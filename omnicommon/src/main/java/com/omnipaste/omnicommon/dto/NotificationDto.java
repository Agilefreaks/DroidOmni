package com.omnipaste.omnicommon.dto;

import com.omnipaste.omnicommon.Provider;

public class NotificationDto {
  private Provider provider;
  private String registrationId;

  public NotificationDto() {
  }

  public NotificationDto(Provider provider, String registrationId) {

    this.provider = provider;
    this.registrationId = registrationId;
  }

  public Provider getProvider() {
    return provider;
  }

  public String getRegistrationId() {
    return registrationId;
  }
}
