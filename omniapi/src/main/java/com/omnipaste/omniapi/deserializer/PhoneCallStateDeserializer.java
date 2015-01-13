package com.omnipaste.omniapi.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.omnipaste.omnicommon.dto.PhoneCallDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PhoneCallStateDeserializer implements JsonDeserializer<PhoneCallDto.State> {
  private static final Map<String, PhoneCallDto.State> MAP = new HashMap<String, PhoneCallDto.State>() {
    {
      put("incoming", PhoneCallDto.State.INCOMING);
      put("initiate", PhoneCallDto.State.INITIATE);
      put("end_call", PhoneCallDto.State.END_CALL);
      put("hold", PhoneCallDto.State.HOLD);
    }
  };

  @Override
  public PhoneCallDto.State deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String key = jsonElement.getAsString();
    return MAP.containsKey(key) ? MAP.get(key) : PhoneCallDto.State.UNKNOWN;
  }
}
