package com.omnipasteapp.omnicommon.interfaces;

public interface IConfigurationProvider {
  String getValue(String key);

  int getValue(String key, int defaultValue);

  boolean setValue(String key, String value);

  boolean setValue(String  key, int value);
}
