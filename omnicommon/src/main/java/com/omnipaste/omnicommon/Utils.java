package com.omnipaste.omnicommon;

public class Utils {
  @SafeVarargs
  public static <T> T firstNotNuLL(T...args){
    for (T arg : args)
      if ( arg != null) return arg;
    throw new NullPointerException();
  }
}
