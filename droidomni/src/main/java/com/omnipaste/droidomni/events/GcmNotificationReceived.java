package com.omnipaste.droidomni.events;

import android.os.Bundle;

import com.omnipaste.omnicommon.Target;

public class GcmNotificationReceived {
  public static String REGISTRATION_ID_KEY = "registrationId";
  public static String PROVIDER_KEY = "provider";

  private Target provider;
  private String registrationId;
  private Bundle extras;

  public GcmNotificationReceived() {
  }

  public GcmNotificationReceived(Bundle extras) {
    this.extras = extras;
    registrationId = extras.getString(REGISTRATION_ID_KEY);
    provider = Target.valueOf(extras.getString(PROVIDER_KEY));
  }

  public Target getProvider() {
    return provider;
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public Bundle getExtras() {
    return extras;
  }
}
