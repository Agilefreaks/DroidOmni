package com.omnipasteapp.omnicommon.services;

import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

public class ConfigurationService implements IConfigurationService {
  private CommunicationSettings communicationSettings;
  private IConfigurationProvider provider;

  @Inject
  public ConfigurationService(IConfigurationProvider provider) {
    this.provider = provider;
  }

  @Override
  public CommunicationSettings getCommunicationSettings() {
    return communicationSettings;
  }

  @Override
  public void loadCommunicationSettings() {
    String channel = provider.getValue(CommunicationSettings.ChannelKey);
    communicationSettings = new CommunicationSettings(channel);
  }

  @Override
  public void updateCommunicationSettings() {
    provider.setValue(CommunicationSettings.ChannelKey, communicationSettings.getChannel());
  }
}
