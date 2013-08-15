package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IAppConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.ApiConfig;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnicommon.settings.Resources;

import javax.inject.Inject;

public class ConfigurationService implements IConfigurationService {
  private CommunicationSettings communicationSettings;
  private IConfigurationProvider _provider;
  private final IConfigurationProvider configurationProvider;
  private final IAppConfigurationProvider appConfigurationProvider;

  @Inject
  public ConfigurationService(IConfigurationProvider provider) {
    this.configurationProvider = configurationProvider;
    this.appConfigurationProvider = appConfigurationProvider;
  }

  @Override
  public CommunicationSettings getCommunicationSettings() {
    if (communicationSettings == null) {
      initialize();
    }

    return communicationSettings;
  }

  @Override
  public boolean loadCommunicationSettings() {
    String channel = _provider.getValue(CommunicationSettings.ChannelKey);
    _communicationSettings = new CommunicationSettings(channel);
    return channel != null && !channel.isEmpty();
  public void initialize() {
    String channel = configurationProvider.getValue(CommunicationSettings.ChannelKey);
    communicationSettings = new CommunicationSettings(channel);

    apiConfig = new ApiConfig();
    apiConfig.setBaseUrl(appConfigurationProvider.getValue(ApiConfig.BaseUrlKey));
    Resources resources = new Resources();
    resources.setClippings(appConfigurationProvider.getValue(Resources.ClippingsKey));
    apiConfig.setResources(resources);
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