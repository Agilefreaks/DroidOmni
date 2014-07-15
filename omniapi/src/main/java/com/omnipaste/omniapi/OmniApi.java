package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Events;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

public interface OmniApi {
  public Devices devices();

  public Clippings clippings();

  public Token token();

  public Events notifications();

  public void setAccessToken(AccessTokenDto accessToken);
}
