package com.omnipasteapp.omnipaste;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;

public class ConfigurationProvider implements IConfigurationProvider {
  @Override
  public String getValue(String s) {
    return "calinuswork@gmail.com";
  }

  @Override
  public boolean setValue(String s, String s2) {
    return true;
  }
}
