package com.omnipaste.phoneprovider.actions;

import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;

public abstract class Action {
  protected Context context;

  public static <T extends Action> T build(Class<T> clazz, Context context) {
    T result = null;

    try {
      result = clazz.getDeclaredConstructor(Context.class).newInstance(context);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ignore) {
    }

    return result;
  }

  public Action(Context context) {
    this.context = context;
  }

  public abstract void execute(Bundle extras);
}
