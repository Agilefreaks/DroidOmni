package com.omnipaste.omniapi.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SmsMessageTypeSerializer implements JsonSerializer<SmsMessageDto.Type> {
  private static final Map<SmsMessageDto.Type, JsonPrimitive> MAP = new HashMap<SmsMessageDto.Type, JsonPrimitive>() {
    {
      put(SmsMessageDto.Type.INCOMING, new JsonPrimitive("incoming"));
      put(SmsMessageDto.Type.OUTGOING, new JsonPrimitive("outgoing"));
    }
  };

  @Override
  public JsonElement serialize(SmsMessageDto.Type src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
