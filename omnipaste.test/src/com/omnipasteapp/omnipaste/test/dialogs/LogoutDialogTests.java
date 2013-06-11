package com.omnipasteapp.omnipaste.test.dialogs;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.dialogs.LogoutDialog;
import com.omnipasteapp.omnipaste.services.IIntentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
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

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IConfigurationService.class).toInstance(configurationService);
      bind(IIntentService.class).toInstance(intentService);
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
  public void logoutSetsChannelToNull(){
    subject.logout();

    verify(communicationSettings).setChannel(isNull(String.class));
  }

  @Test
  public void logoutCallsConfigurationServiceUpdateCommunicationSettings(){
    subject.logout();

    verify(configurationService).updateCommunicationSettings();
  }

  @Test
  public void onClickAlwaysCallsIntentServiceStopBackgroundService(){
    subject.onClick(null,0);

    verify(intentService).stopService(eq(BackgroundService.class));
  }

  @Test
  public void onClickAlwaysNavigatesToMainPage(){
    subject.onClick(null, 0);

    verify(intentService).startActivity(eq(MainActivity.class));
  }
}
