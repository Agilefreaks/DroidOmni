package com.omnipaste.omnicommon.dto;

import java.util.Locale;

public class NotificationDto {
  private Type type;
  private String id;

  public enum Type {
    CLIPPING_CREATED,
    SEND_SMS_MESSAGE_REQUESTED,
    START_PHONE_CALL_REQUESTED,
    END_PHONE_CALL_REQUESTED,
    PHONE_CALL_RECEIVED,
    SMS_MESSAGE_RECEIVED,
    UNKNOWN;

    public static Type parse(String type) {
      Type result = Type.UNKNOWN;

      try {
        result = Type.valueOf(type.toUpperCase(Locale.getDefault()));
      } catch (IllegalArgumentException ignore) {
      }

      return result;
    }
  }

  public NotificationDto(Type type, String id) {
    this.type = type;
    this.id = id;
  }

  public Type getType() {
    return type;
  }

  public String getId() {
    return id;
  }
}
