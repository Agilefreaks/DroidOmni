package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

public interface IConfigurationService {
  public CommunicationSettings get_communicationSettings();

  public void loadCommunicationSettings();

  public void updateCommunicationSettings();

  public void clearChannel();
}
