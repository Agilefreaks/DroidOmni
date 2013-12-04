package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import javax.inject.Inject;

public class ConfigurationService implements IConfigurationService {
  public static final String AppVersionKey = "appVersion";

  private final IConfigurationProvider configurationProvider;
  private CommunicationSettings communicationSettings;

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
  public int getAppVersion() {
    return configurationProvider.getValue(AppVersionKey, Integer.MIN_VALUE);
  }

  @Override
  public void updateAppVersion(int appVersion) {
    configurationProvider.setValue(ConfigurationService.AppVersionKey, appVersion);
  }

  @Override
  public void updateCommunicationSettings() {
    configurationProvider.setValue(CommunicationSettings.ChannelKey, communicationSettings.getChannel());
    configurationProvider.setValue(CommunicationSettings.RegistrationIdKey, communicationSettings.getRegistrationId());
  }

  @Override
  public void clearChannel() {
    configurationProvider.setValue(CommunicationSettings.ChannelKey, null);
    initialize();
  }

  private void initialize() {
    String channel = configurationProvider.getValue(CommunicationSettings.ChannelKey);
    String registrationId = configurationProvider.getValue(CommunicationSettings.RegistrationIdKey);
    communicationSettings = new CommunicationSettings(channel, registrationId);
  }
}