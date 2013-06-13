package com.omnipasteapp.omnipaste.test.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.dialogs.GoogleLoginDialog;
import com.omnipasteapp.omnipaste.services.IIntentService;
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
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class GoogleLoginDialogTests {
  private GoogleLoginDialog subject;

  @Mock
  private AccountManager accountManager;

  @Mock
  private IIntentService intentService;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private CommunicationSettings communicationSettings;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(AccountManager.class).toInstance(accountManager);
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

    subject = RoboGuice.getInjector(Robolectric.application).getInstance(GoogleLoginDialog.class);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void accountsCallsAccountManagerGetGoogleAccounts(){
    when(accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{});

    subject.accounts();
    subject.accounts();

    verify(accountManager, atMost(1)).getAccountsByType(eq("com.google"));
  }

  @Test
  public void onAccountSelectedCallsSettingsSetChannel(){
    subject.login(new Account("user", "type"));

    verify(communicationSettings).setChannel(eq("user"));
  }

  @Test
  public void onAccountSelectedConfigurationServiceUpdateCommunicationSettings(){
    subject.login(new Account("user", "type"));

    verify(configurationService).updateCommunicationSettings();
  }

  @Test
  public void onAccountSelectedCallsStartBackgroundService(){
    subject.login(new Account("name", "type"));

    verify(intentService).startService(eq(BackgroundService.class));
  }

  @Test
  public void onAccountSelectedCallsStartClearActivityMain(){
    subject.login(new Account("name", "type"));

    verify(intentService).startClearActivity(eq(MainActivity.class));
  }
}
