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
  private ApiConfig apiConfig;
  private final IConfigurationProvider configurationProvider;
  private final IAppConfigurationProvider appConfigurationProvider;

  @Inject
  public ConfigurationService(IConfigurationProvider configurationProvider, IAppConfigurationProvider appConfigurationProvider) {
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
  public ApiConfig getApiConfig() {
    return apiConfig;
  }

  @Override
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