package com.omnipaste.droidomni.events;

import android.os.Bundle;

import com.omnipaste.omnicommon.Provider;

public class GcmEvent {
  public static String REGISTRATION_ID_KEY = "registrationId";
  public static String COLLAPSE_KEY = "collapse_key";

  private Provider provider;
  private String registrationId;

  public GcmEvent() {
  }

  public GcmEvent(Bundle extras) {
    registrationId = extras.getString(REGISTRATION_ID_KEY);
    provider = Provider.valueOf(extras.getString(COLLAPSE_KEY));
  }

  public Provider getProvider() {
    return provider;
  }

  public String getRegistrationId() {
    return registrationId;
  }
}
