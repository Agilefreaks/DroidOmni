package com.omnipasteapp.omnipaste.test;

import android.accounts.Account;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.LoginActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
  private LoginActivity subject;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private CommunicationSettings communicationSettings;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IConfigurationService.class).toInstance(configurationService);
      bind(CommunicationSettings.class).toInstance(communicationSettings);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    when(configurationService.getCommunicationSettings()).thenReturn(communicationSettings);

    subject = Robolectric.buildActivity(LoginActivity.class).create().get();
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void onAccountSelectedCallsSettingsSetChannel(){
    subject.onAccountSelected(new Account("user", "type"));

    verify(communicationSettings).setChannel(eq("user"));
  }

  @Test
  public void onAccountSelectedConfigurationServiceUpdateCommunicationSettings(){
    subject.onAccountSelected(new Account("user", "type"));

    verify(configurationService).updateCommunicationSettings();
  }
}
