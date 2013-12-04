package com.omnipasteapp.omniapi.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipasteapp.omnicommon.models.ClippingType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClippingTypeDeserializer implements JsonDeserializer<ClippingType> {
  private static final Map<String, ClippingType> MAP = new HashMap<String, ClippingType>() {
    {
      put("phone_number", ClippingType.PhoneNumber);
      put("uri", ClippingType.URI);
    }
  };

  @Override
  public ClippingType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : ClippingType.Unknown;
  }
}
