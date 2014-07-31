package com.omnipaste.omniapi.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.ClippingDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClippingTypeDeserializer implements JsonDeserializer<ClippingDto.ClippingType> {
  private static final Map<String, ClippingDto.ClippingType> MAP = new HashMap<String, ClippingDto.ClippingType>() {
    {
      put("phone_number", ClippingDto.ClippingType.PHONE_NUMBER);
      put("URL", ClippingDto.ClippingType.URL);
      put("ADDRESS", ClippingDto.ClippingType.ADDRESS);
      put("UNKNOWN", ClippingDto.ClippingType.UNKNOWN);
    }
  };

  @Override
  public ClippingDto.ClippingType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : ClippingDto.ClippingType.UNKNOWN;
  }
}
