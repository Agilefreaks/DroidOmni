package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import javax.inject.Inject;

public class ConfigurationService implements IConfigurationService {
  private CommunicationSettings _communicationSettings;
  private IConfigurationProvider _provider;

  @Inject
  public ConfigurationService(IConfigurationProvider provider) {
    _provider = provider;
  }

  @Override
  public CommunicationSettings getCommunicationSettings() {
    return _communicationSettings;
  }

  @Override
  public boolean loadCommunicationSettings() {
    String channel = _provider.getValue(CommunicationSettings.ChannelKey);
    _communicationSettings = new CommunicationSettings(channel);

    return channel != null && !channel.isEmpty();
  }

  @Override
  public void updateCommunicationSettings() {
    _provider.setValue(CommunicationSettings.ChannelKey, _communicationSettings.getChannel());
  }

  @Override
  public void clearChannel() {
    _provider.setValue(CommunicationSettings.ChannelKey, null);
    loadCommunicationSettings();
  }
}