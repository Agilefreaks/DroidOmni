package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

public interface IConfigurationService {
  public CommunicationSettings getCommunicationSettings();

  public void loadCommunicationSettings();

  public void updateCommunicationSettings();
}
