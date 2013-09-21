package com.omnipasteapp.omniclipboard.api.builders;

import com.google.gson.Gson;

import java.io.BufferedReader;

public abstract class JsonBuilder<TModel> {
  public abstract Class<TModel> getType();

  public TModel build(BufferedReader in) {
    Gson gson = new Gson();

    return gson.fromJson(new BufferedReader(in), getType());
  }
}
