package com.omnipaste.omnicommon.service;

import com.omnipaste.omnicommon.domain.Configuration;

public interface  ConfigurationService {
  public Configuration getConfiguration();

  public void setConfiguration(Configuration configuration);

  public boolean isClipboardNotificationEnabled();

  public boolean isTelephonyServiceEnabled();

  public boolean isTelephonyNotificationEnabled();

  public boolean isGcmWorkAroundEnabled();
}
