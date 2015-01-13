package com.omnipaste.omniapi.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.SmsMessageDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SmsMessageStateDeserializer implements JsonDeserializer<SmsMessageDto.State> {
  private static final Map<String, SmsMessageDto.State> MAP = new HashMap<String, SmsMessageDto.State>() {
    {
      put("incoming", SmsMessageDto.State.INCOMING);
      put("initiate", SmsMessageDto.State.INITIATE);
    }
  };

  @Override
  public SmsMessageDto.State deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : SmsMessageDto.State.UNKNOWN;
  }
}
