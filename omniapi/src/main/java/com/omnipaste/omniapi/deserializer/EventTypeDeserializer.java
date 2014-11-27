package com.omnipaste.omniapi.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.EventDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EventTypeDeserializer implements JsonDeserializer<EventDto.EventType> {
  private static final Map<String, EventDto.EventType> MAP = new HashMap<String, EventDto.EventType>() {
    {
      put("IncomingCallEvent", EventDto.EventType.INCOMING_CALL_EVENT);
      put("IncomingSmsEvent", EventDto.EventType.INCOMING_SMS_EVENT);
    }
  };

  @Override
  public EventDto.EventType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : EventDto.EventType.UNKNOWN;
  }
}
