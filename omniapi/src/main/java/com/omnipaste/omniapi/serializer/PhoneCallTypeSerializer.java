package com.omnipaste.omniapi.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PhoneCallTypeSerializer implements JsonSerializer<PhoneCallDto.Type> {
  private static final Map<PhoneCallDto.Type, JsonPrimitive> MAP = new HashMap<PhoneCallDto.Type, JsonPrimitive>() {
    {
      put(PhoneCallDto.Type.INCOMING, new JsonPrimitive("incoming"));
      put(PhoneCallDto.Type.OUTGOING, new JsonPrimitive("outgoing"));
    }
  };

  @Override
  public JsonElement serialize(PhoneCallDto.Type src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
