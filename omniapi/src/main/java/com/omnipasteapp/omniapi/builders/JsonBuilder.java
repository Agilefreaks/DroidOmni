package com.omnipasteapp.omniapi.builders;

import com.google.gson.GsonBuilder;
import com.omnipasteapp.omniapi.deserializers.ClippingTypeDeserializer;
import com.omnipasteapp.omnicommon.models.ClippingType;

import java.io.BufferedReader;

public abstract class JsonBuilder<TModel> {
  public abstract Class<TModel> getType();

  public TModel build(BufferedReader in) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(ClippingType.class, new ClippingTypeDeserializer());

    return gsonBuilder.create().fromJson(new BufferedReader(in), getType());
  }
}
