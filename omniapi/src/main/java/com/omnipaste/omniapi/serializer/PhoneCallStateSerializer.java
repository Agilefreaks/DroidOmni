package com.omnipaste.omniapi.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PhoneCallStateSerializer implements JsonSerializer<PhoneCallDto.State> {
  private static final Map<PhoneCallDto.State, JsonPrimitive> MAP = new HashMap<PhoneCallDto.State, JsonPrimitive>() {
    {
      put(PhoneCallDto.State.STARTING, new JsonPrimitive("starting"));
      put(PhoneCallDto.State.STARTED, new JsonPrimitive("started"));
      put(PhoneCallDto.State.ENDED, new JsonPrimitive("ended"));
      put(PhoneCallDto.State.ENDING, new JsonPrimitive("ending"));
    }
  };

  @Override
  public JsonElement serialize(PhoneCallDto.State src, Type typeOfSrc, JsonSerializationContext context) {
    return MAP.containsKey(src) ? MAP.get(src) : new JsonPrimitive("UNKNOWN");
  }
}
