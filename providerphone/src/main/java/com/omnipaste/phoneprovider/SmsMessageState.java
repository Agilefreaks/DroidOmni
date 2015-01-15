package com.omnipaste.phoneprovider;

import java.util.Locale;

public enum SmsMessageState {
  INCOMING,
  INITIATE,
  UNKNOWN;

  public static SmsMessageState parse(String state) {
    SmsMessageState result = SmsMessageState.UNKNOWN;

    try {
      result = SmsMessageState.valueOf(state.toUpperCase(Locale.getDefault()));
    } catch (IllegalArgumentException ignore) {
    }

    return result;
  }
}
