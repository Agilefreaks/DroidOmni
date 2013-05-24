package com.omnipasteapp.omnicommon.interfaces;

public interface IConfigurationProvider {
  String getValue(String key);

  boolean setValue(String key, String value);
}
