package com.omnipaste.omniapi.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SmsMessageStateSerializer implements JsonSerializer<SmsMessageDto.State> {
  private static final Map<SmsMessageDto.State, JsonPrimitive> MAP = new HashMap<SmsMessageDto.State, JsonPrimitive>() {
    {
      put(SmsMessageDto.State.RECEIVED, new JsonPrimitive("received"));
      put(SmsMessageDto.State.SENDING, new JsonPrimitive("sending"));
      put(SmsMessageDto.State.SENT, new JsonPrimitive("sent"));
    }
  };

  @Override
  public JsonElement serialize(SmsMessageDto.State src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
