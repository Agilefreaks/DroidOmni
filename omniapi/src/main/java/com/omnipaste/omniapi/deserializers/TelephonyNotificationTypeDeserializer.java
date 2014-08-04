package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.TelephonyEventDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TelephonyNotificationTypeDeserializer implements JsonDeserializer<TelephonyEventDto.TelephonyEventType> {
  private static final Map<String, TelephonyEventDto.TelephonyEventType> MAP = new HashMap<String, TelephonyEventDto.TelephonyEventType>() {
    {
      put("incoming_call", TelephonyEventDto.TelephonyEventType.incomingCall);
      put("incoming_sms", TelephonyEventDto.TelephonyEventType.incomingSms);
    }
  };

  @Override
  public TelephonyEventDto.TelephonyEventType deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : TelephonyEventDto.TelephonyEventType.unknown;
  }
}
