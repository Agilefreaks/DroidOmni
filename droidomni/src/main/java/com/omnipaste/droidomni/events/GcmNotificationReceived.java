package com.omnipaste.droidomni.events;

import android.os.Bundle;

import com.omnipaste.omnicommon.dto.NotificationDto;

public class GcmNotificationReceived {
  public static String REGISTRATION_ID_KEY = "registrationId";
  public static String PROVIDER_KEY = "provider";

  private NotificationDto.Target provider;
  private String registrationId;
  private Bundle extras;

  public static <T extends Enum<T>> T valueOfOrDefault(Class<T> enumeration, String name, T defaultValue) {
    for (T enumValue : enumeration.getEnumConstants()) {
      if (enumValue.name().equalsIgnoreCase(name)) {
        return enumValue;
      }
    }

    return defaultValue;
  }

  public GcmNotificationReceived() {
  }

  public GcmNotificationReceived(Bundle extras) {
    this.extras = extras;
    registrationId = extras.getString(REGISTRATION_ID_KEY);
    provider = valueOfOrDefault(NotificationDto.Target.class, extras.getString(PROVIDER_KEY), NotificationDto.Target.unknown);
  }


  public NotificationDto.Target getProvider() {
    return provider;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public Bundle getExtras() {
    return extras;
  }
}
