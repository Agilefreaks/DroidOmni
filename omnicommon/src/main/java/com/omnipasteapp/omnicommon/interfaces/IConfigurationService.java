package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

public interface IConfigurationService {
  public CommunicationSettings getCommunicationSettings();

  public boolean loadCommunicationSettings();

  public void updateCommunicationSettings();

  public void clearChannel();
}
