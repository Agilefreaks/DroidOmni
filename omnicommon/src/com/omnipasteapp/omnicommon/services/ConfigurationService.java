package com.omnipasteapp.omnicommon.services;

import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

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
  public void loadCommunicationSettings() {
    String channel = _provider.getValue(CommunicationSettings.ChannelKey);
    _communicationSettings = new CommunicationSettings(channel);
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
