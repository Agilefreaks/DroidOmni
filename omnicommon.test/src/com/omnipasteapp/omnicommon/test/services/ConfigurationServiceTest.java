package com.omnipasteapp.omnicommon.test.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class ConfigurationServiceTest {
  private IConfigurationProvider configurationProvider;
  private IConfigurationService subject;

  @Before
  public void Setup() {
    configurationProvider = mock(IConfigurationProvider.class);
    subject = new ConfigurationService(configurationProvider);
  }

  @Test
  public void loadCommunicationSettingsCallsConfigurationProviderGetValueChannelKey(){
    subject.loadCommunicationSettings();
    verify(configurationProvider).getValue(CommunicationSettings.ChannelKey);
  }
}
