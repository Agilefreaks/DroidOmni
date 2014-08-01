package com.omnipaste.phoneprovider;

public enum PhoneAction {
  CALL,
  END_CALL,
  UNKNOWN;

  public static PhoneAction parse(String phoneAction) {
    PhoneAction result = PhoneAction.UNKNOWN;

    try {
      result = PhoneAction.valueOf(phoneAction.toUpperCase());
    } catch (IllegalArgumentException ignore) {
    }

    return result;
  }
}
