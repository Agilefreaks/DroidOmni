package com.omnipaste.omniapi.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TelephonyNotificationTypeSerializer implements JsonSerializer<TelephonyEventDto.TelephonyEventType> {
  private static final Map<TelephonyEventDto.TelephonyEventType, JsonPrimitive> MAP = new HashMap<TelephonyEventDto.TelephonyEventType, JsonPrimitive>() {
    {
      put(TelephonyEventDto.TelephonyEventType.incomingCall, new JsonPrimitive("incoming_call"));
      put(TelephonyEventDto.TelephonyEventType.incomingSms, new JsonPrimitive("incoming_sms"));
    }
  };

  @Override
  public JsonElement serialize(TelephonyEventDto.TelephonyEventType src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
