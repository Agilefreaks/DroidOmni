package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import javax.inject.Inject;

public class ConfigurationService implements IConfigurationService {
  private CommunicationSettings communicationSettings;
  private final IConfigurationProvider configurationProvider;

  @Inject
  public ConfigurationService(IConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Override
  public CommunicationSettings getCommunicationSettings() {
    if (communicationSettings == null) {
      initialize();
    }

    return communicationSettings;
  }

  @Override
  public void initialize() {
    String channel = configurationProvider.getValue(CommunicationSettings.ChannelKey);
    communicationSettings = new CommunicationSettings(channel);
  }

  @Override
  public void updateCommunicationSettings() {
    configurationProvider.setValue(CommunicationSettings.ChannelKey, communicationSettings.getChannel());
  }

  @Override
  public void clearChannel() {
    configurationProvider.setValue(CommunicationSettings.ChannelKey, null);
    initialize();
  }
}