package com.omnipaste.omniapi.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.TelephonyNotificationDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TelephonyNotificationTypeSerializer implements JsonSerializer<TelephonyNotificationDto.TelephonyNotificationType> {
  private static final Map<TelephonyNotificationDto.TelephonyNotificationType, JsonPrimitive> MAP = new HashMap<TelephonyNotificationDto.TelephonyNotificationType, JsonPrimitive>() {
    {
      put(TelephonyNotificationDto.TelephonyNotificationType.incomingCall, new JsonPrimitive("incoming_call"));
      put(TelephonyNotificationDto.TelephonyNotificationType.incomingSms, new JsonPrimitive("incoming_sms"));
    }
  };

  @Override
  public JsonElement serialize(TelephonyNotificationDto.TelephonyNotificationType src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
