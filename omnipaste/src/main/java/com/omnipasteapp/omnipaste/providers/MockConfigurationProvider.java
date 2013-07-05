package com.omnipasteapp.omnipaste.providers;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;

@SuppressWarnings("UnusedDeclaration")
public class MockConfigurationProvider implements IConfigurationProvider {
  @Override
  public String getValue(String s) {
    return "calinuswork@gmail.com";
  }

  @Override
  public boolean setValue(String s, String s2) {
    return true;
  }
}
