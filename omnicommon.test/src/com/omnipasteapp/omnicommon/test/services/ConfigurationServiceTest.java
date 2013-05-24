package com.omnipasteapp.omnicommon.test.services;

import com.omnipasteapp.omnicommon.interfaces.IConfigurationProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.services.ConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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
