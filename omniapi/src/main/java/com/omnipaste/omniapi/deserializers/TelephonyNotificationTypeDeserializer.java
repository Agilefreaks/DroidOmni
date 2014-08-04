package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TelephonyNotificationTypeDeserializer implements JsonDeserializer<TelephonyEventDto.TelephonyNotificationType> {
  private static final Map<String, TelephonyEventDto.TelephonyNotificationType> MAP = new HashMap<String, TelephonyEventDto.TelephonyNotificationType>() {
    {
      put("incoming_call", TelephonyEventDto.TelephonyNotificationType.incomingCall);
      put("incoming_sms", TelephonyEventDto.TelephonyNotificationType.incomingSms);
    }
  };

  @Override
  public TelephonyEventDto.TelephonyNotificationType deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : TelephonyEventDto.TelephonyNotificationType.unknown;
  }
}
