package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.TelephonyNotificationDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TelephonyNotificationTypeDeserializer implements JsonDeserializer<TelephonyNotificationDto.TelephonyNotificationType> {
  private static final Map<String, TelephonyNotificationDto.TelephonyNotificationType> MAP = new HashMap<String, TelephonyNotificationDto.TelephonyNotificationType>() {
    {
      put("incoming_call", TelephonyNotificationDto.TelephonyNotificationType.incomingCall);
      put("incoming_sms", TelephonyNotificationDto.TelephonyNotificationType.incomingSms);
    }
  };

  @Override
  public TelephonyNotificationDto.TelephonyNotificationType deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : TelephonyNotificationDto.TelephonyNotificationType.unknown;
  }
}
