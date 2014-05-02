package com.omnipaste.omniapi;

import com.omnipaste.omniapi.resources.v1.Clippings;
import com.omnipaste.omniapi.resources.v1.Devices;
import com.omnipaste.omniapi.resources.v1.Notifications;
import com.omnipaste.omniapi.resources.v1.Token;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

public interface OmniApi {
  public Devices devices();

  public Clippings clippings();

  public Token token();

  public Notifications notifications();

  public void setAccessToken(AccessTokenDto accessToken);
}
