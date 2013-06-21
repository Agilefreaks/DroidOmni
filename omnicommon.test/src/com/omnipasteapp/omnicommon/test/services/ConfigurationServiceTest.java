package com.omnipasteapp.omnicommon.test.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationServiceTest {
  private IConfigurationProvider configurationProvider;
  private IConfigurationService subject;

  @Before
  public void Setup() {
    configurationProvider = mock(IConfigurationProvider.class);
    when(configurationProvider.getValue(CommunicationSettings.ChannelKey)).thenReturn("channel");

    subject = new ConfigurationService(configurationProvider);
  }

  @Test
  public void loadCommunicationSettingsCallsConfigurationProviderGetValueChannelKey() {
    subject.loadCommunicationSettings();

    verify(configurationProvider).getValue(CommunicationSettings.ChannelKey);
  }

  @Test
  public void updateCommunicationSettingsAlwaysCallsProviderSetValueForChannel() {
    subject.loadCommunicationSettings();

    subject.updateCommunicationSettings();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), eq("channel"));
  }

  @Test
  public void clearChannelCallSetChannelToNull() {
    subject.clearChannel();

    verify(configurationProvider).setValue(eq(CommunicationSettings.ChannelKey), isNull(String.class));
  }
}
