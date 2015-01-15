package com.omnipaste.phoneprovider;

import java.util.Locale;

public enum PhoneCallState {
  INCOMING,
  INITIATE,
  END_CALL,
  UNKNOWN;

  public static PhoneCallState parse(String state) {
    PhoneCallState result = PhoneCallState.UNKNOWN;

    try {
      result = PhoneCallState.valueOf(state.toUpperCase(Locale.getDefault()));
    } catch (IllegalArgumentException ignore) {
    }

    return result;
  }
}
