package com.omnipaste.phoneprovider;

import java.util.Locale;

public enum PhoneAction {
  CALL,
  END_CALL,
  SMS,
  UNKNOWN;

  public static PhoneAction parse(String phoneAction) {
    PhoneAction result = PhoneAction.UNKNOWN;

    try {
      result = PhoneAction.valueOf(phoneAction.toUpperCase(Locale.getDefault()));
    } catch (IllegalArgumentException ignore) {
    }

    return result;
  }
}
