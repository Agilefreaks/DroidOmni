package com.omnipasteapp.omnipaste.test.dialogs;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.omnipasteapp.omnipaste.dialogs.GoogleLoginDialog;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.RoboGuice;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class GoogleLoginDialogTests {
  private GoogleLoginDialog subject;

  @Mock
  private AccountManager accountManager;

  public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(AccountManager.class).toInstance(accountManager);
    }
  }

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    RoboGuice
            .setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application))
                    .with(new TestModule()));

    subject = RoboGuice.getInjector(Robolectric.application).getInstance(GoogleLoginDialog.class);
  }

  @After
  public void tearDown() {
    subject.setListener(null);
    RoboGuice.util.reset();
  }

  @Test
  public void initAccountsCallsAccountManagerGetGoogleAccounts(){
    when(accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{});

    subject.initAccounts();

    verify(accountManager).getAccountsByType(eq("com.google"));
  }

  @Test
  public void onClickWhenIndexIsInTheAccountsRangeAndListenerIsNotNullCallsOnAccountSelected(){
    final Account account = new Account("user@gmail.com", "com.google");
    when(accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{ account });
    subject.initAccounts();
    subject.setListener(new GoogleLoginDialog.Listener() {
      @Override
      public void onAccountSelected(Account acct) {
        assertEquals(acct, account);
      }
    });

    subject.onClick(null, 0);
  }

  @Test
  public void onClickWhenIndexIsNegativeDoesNotCallOnAccountSelected(){
    final Account account = new Account("user@gmail.com", "com.google");
    when(accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{ account });
    subject.initAccounts();
    subject.setListener(new GoogleLoginDialog.Listener() {
      @Override
      public void onAccountSelected(Account acct) {
        Assert.fail("Should not be called");
      }
    });

    subject.onClick(null, -1);
  }

  @Test
  public void onClickWhenIndexIsBiggerThanTheAccountCountDoesNotCallOnAccountSelected(){
    final Account account = new Account("user@gmail.com", "com.google");
    when(accountManager.getAccountsByType(eq("com.google"))).thenReturn(new Account[]{ account });
    subject.initAccounts();
    subject.setListener(new GoogleLoginDialog.Listener() {
      @Override
      public void onAccountSelected(Account acct) {
        Assert.fail("Should not be called");
      }
    });

    subject.onClick(null, 1);
  }
}
