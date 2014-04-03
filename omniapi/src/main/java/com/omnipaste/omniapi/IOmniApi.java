package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Token;

public interface IOmniApi {
  public Devices devices();

  public Clippings clippings();

  public Token token();
}
