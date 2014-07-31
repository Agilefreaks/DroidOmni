package com.omnipaste.omnicommon.dto;

import android.os.Bundle;

public class NotificationDto {
  private Target target;
  private String registrationId;
  private Bundle extra;

  public enum Target {
    CLIPBOARD,
    PHONE,
    UNKNOWN
  }
  public NotificationDto() {
  }

  public NotificationDto(Target target, String registrationId) {
    this.target = target;
    this.registrationId = registrationId;
  }

  public NotificationDto(Target target, String registrationId, Bundle extra) {
    this(target, registrationId);
    this.extra = extra;
  }

  public Target getTarget() {
    return target;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public Bundle getExtra() {
    return extra;
  }
}
