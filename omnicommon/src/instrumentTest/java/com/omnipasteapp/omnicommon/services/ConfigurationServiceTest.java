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
    when(configurationProvider.getValue(CommunicationSettings.RegistrationIdKey)).thenReturn("registration_id");
    when(configurationProvider.getValue(ConfigurationService.AppVersionKey, 1)).thenReturn(2);

    subject = new ConfigurationService(configurationProvider);
  }

  public void testInitializeCallsConfigurationProviderGetValueChannelKey() {
    subject.getCommunicationSettings();

    verify(configurationProvider).getValue(CommunicationSettings.ChannelKey);
  }

  public void testInitializeCallsConfigurationProviderGetValueRegistrationIdKey() {
    subject.getCommunicationSettings();

    verify(configurationProvider).getValue(CommunicationSettings.RegistrationIdKey);
  }

  public void testGetCommunicationSettingsWillNotFailIfNotCalledLoad() {
    assertNotNull(subject.getCommunicationSettings());
  }

  public void testUpdateCommunicationSettingsAlwaysCallsProviderSetValueForChannel() {
    subject.getCommunicationSettings();

    subject.updateCommunicationSettings();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), eq("channel"));
  }

  public void testUpdateCommunicationSettingsAlwaysCallsProviderSetValueForRegistrationId() {
    subject.getCommunicationSettings();

    subject.updateCommunicationSettings();

    verify(configurationProvider).setValue(eq(CommunicationSettings.RegistrationIdKey), eq("registration_id"));
  }

  public void testClearChannelCallSetChannelToNull() {
    subject.clearChannel();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), isNull(String.class));
  }

  public void testGetAppVersionCallsGetValue() {
    subject.getAppVersion();

    verify(configurationProvider).getValue(eq(ConfigurationService.AppVersionKey), eq(Integer.MIN_VALUE));
  }

  public void testSetAppVersionCallsSetValue() {
    subject.updateAppVersion(2);

    verify(configurationProvider).setValue(eq(ConfigurationService.AppVersionKey), eq(2));
  }
}
