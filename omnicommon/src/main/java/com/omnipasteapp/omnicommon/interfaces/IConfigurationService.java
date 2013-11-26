package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

public interface IConfigurationService {
  public CommunicationSettings getCommunicationSettings();

  public int getAppVersion();

  public void updateAppVersion(int appVersion);

  public void updateCommunicationSettings();

  public void clearChannel();
}
