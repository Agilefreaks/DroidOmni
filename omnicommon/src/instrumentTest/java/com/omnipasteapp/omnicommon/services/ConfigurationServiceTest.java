package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import junit.framework.TestCase;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfigurationServiceTest extends TestCase {
  private IConfigurationProvider configurationProvider;
  private IConfigurationService subject;

  public void setUp() {
    configurationProvider = mock(IConfigurationProvider.class);
    when(configurationProvider.getValue(CommunicationSettings.ChannelKey)).thenReturn("channel");

    subject = new ConfigurationService(configurationProvider);
  }

  public void testLoadCommunicationSettingsCallsConfigurationProviderGetValueChannelKey() {
    subject.initialize();

    verify(configurationProvider).getValue(CommunicationSettings.ChannelKey);
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
