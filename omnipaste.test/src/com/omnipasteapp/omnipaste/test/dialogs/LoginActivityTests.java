package com.omnipasteapp.omnipaste.test.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.BackgroundService;
import com.omnipasteapp.omnipaste.LoginActivity;
import com.omnipasteapp.omnipaste.MainActivity;
import com.omnipasteapp.omnipaste.services.IIntentService;
import com.omnipasteapp.omnipaste.util.ActivityTestModule;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roboguice.RoboGuice;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTests {
  private LoginActivity subject;

  @Mock
  private AccountManager _accountManager;

  @Mock
  private IIntentService _intentService;

  @Mock
  private IConfigurationService configurationService;

  @Mock
  private CommunicationSettings communicationSettings;

  public class TestModule extends ActivityTestModule {
    @Override
    protected void configure() {
      bind(AccountManager.class).toInstance(_accountManager);
      bind(IConfigurationService.class).toInstance(configurationService);
      bind(IIntentService.class).toInstance(_intentService);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    when(configurationService.getCommunicationSettings()).thenReturn(communicationSettings);

    subject = new LoginActivity();
    RoboGuice.getInjector(Robolectric.application).injectMembersWithoutViews(subject);
  }

  @After
  public void tearDown() {
    RoboGuice.util.reset();
  }

  @Test
  public void accountsCallsAccountManagerGetGoogleAccounts() {
    when(_accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{});

    subject.accounts();
    subject.accounts();

    verify(_accountManager, atMost(1)).getAccountsByType(eq("com.google"));
  }

  @Test
  public void onAccountSelectedCallsSettingsSetChannel() {
    subject.login(new Account("user", "type"));

    verify(communicationSettings).setChannel(eq("user"));
  }

  @Test
  public void onAccountSelectedConfigurationServiceUpdateCommunicationSettings() {
    subject.login(new Account("user", "type"));

    verify(configurationService).updateCommunicationSettings();
  }

  @Test
  public void onAccountSelectedCallsStartBackgroundService() {
    subject.login(new Account("name", "type"));

    verify(_intentService).startService(eq(BackgroundService.class));
  }

  @Test
  public void onAccountSelectedCallsStartClearActivityMain() {
    subject.login(new Account("name", "type"));

    verify(_intentService).startClearActivity(eq(MainActivity.class));
  }
}
