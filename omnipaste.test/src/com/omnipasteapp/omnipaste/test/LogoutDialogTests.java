package com.omnipasteapp.omnipaste.test;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundServiceCommandReceiver;
import com.omnipasteapp.omnipaste.LoginActivity;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.services.IIntentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Robolectric;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class LogoutDialogTests {
  private LogoutDialog subject;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private IIntentService intentService;

  @Mock
  private CommunicationSettings communicationSettings;

  @Mock
  private IOmniService omniService;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IConfigurationService.class).toInstance(configurationService);
      bind(IIntentService.class).toInstance(intentService);
      bind(IOmniService.class).toInstance(omniService);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
        .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
            .with(new TestModule()));

    when(configurationService.getCommunicationSettings()).thenReturn(communicationSettings);

    subject = RoboGuice.getInjector(Robolectric.application).getInstance(LogoutDialog.class);
  }

  @Test
  public void logoutCallsConfigurationServiceClearChannel() {
    subject.logout();

    verify(configurationService).clearChannel();
  }

  @Test
  public void onClickAlwaysCallsIntentServiceSendBroadcastBackgroundServiceStop() {
    subject.onClick(null, 0);

    verify(intentService).sendBroadcast(eq(BackgroundServiceCommandReceiver.STOP_SERVICE));
  }

  @Test
  public void onClickAlwaysNavigatesToMainPage() {
    subject.onClick(null, 0);

    verify(intentService).startClearActivity(eq(LoginActivity.class));
  }
}
