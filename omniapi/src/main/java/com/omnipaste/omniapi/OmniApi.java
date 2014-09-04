package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.AuthorizationCodes;
import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Events;
import com.omnipaste.omniapi.resources.v1.Token;

public interface OmniApi {
  public Devices devices();

  public Clippings clippings();

  public Token token();

  public Events events();

  public AuthorizationCodes authorizationCodes();
}
