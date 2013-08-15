package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IAppConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.ApiConfig;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnicommon.settings.Resources;

import junit.framework.TestCase;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTest extends TestCase {
  private IConfigurationProvider configurationProvider;
  private IAppConfigurationProvider appConfigurationProvider;
  private IConfigurationService subject;

  public void setUp() {
    configurationProvider = mock(IConfigurationProvider.class);
    appConfigurationProvider = mock(IAppConfigurationProvider.class);
    when(configurationProvider.getValue(CommunicationSettings.ChannelKey)).thenReturn("channel");

    subject = new ConfigurationService(configurationProvider, appConfigurationProvider);
  }

  public void testLoadCommunicationSettingsCallsConfigurationProviderGetValueChannelKey() {
    subject.initialize();

    verify(configurationProvider).getValue(CommunicationSettings.ChannelKey);
  }

  public void testInitializeAlwaysSetsBaseUrl() {
    when(appConfigurationProvider.getValue(eq(ApiConfig.BaseUrlKey))).thenReturn("http://test.com/api/v1");

    subject.initialize();

    assertEquals("http://test.com/api/v1", subject.getApiConfig().getBaseUrl());
  }

  public void testInitializeAlwaysSetsClippingsResource() {
    when(appConfigurationProvider.getValue(eq(Resources.ClippingsKey))).thenReturn("clippings");

    subject.initialize();

    assertEquals("clippings", subject.getApiConfig().getResources().getClippings());
  }

  public void testGetCommunicationSettingsWillNotFailIfNotCalledLoad() {
    assertNotNull(subject.getCommunicationSettings());
  }

  public void testUpdateCommunicationSettingsAlwaysCallsProviderSetValueForChannel() {
    subject.initialize();

    subject.updateCommunicationSettings();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), eq("channel"));
  }

  public void testClearChannelCallSetChannelToNull() {
    subject.clearChannel();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), isNull(String.class));
  }
}
