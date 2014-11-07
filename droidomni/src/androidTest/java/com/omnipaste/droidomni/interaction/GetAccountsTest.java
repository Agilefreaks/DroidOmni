package com.omnipaste.droidomni.interaction;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.test.InstrumentationTestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetAccountsTest extends InstrumentationTestCase {
  private GetAccounts subject;
  private AccountManager mockAccountManager;

  public void setUp() throws Exception {
    super.setUp();

    mockAccountManager = mock(AccountManager.class);
    subject = new GetAccounts(mockAccountManager);
  }

  public void testFromGoogleWillReturnOnlyTheEmailAddresses() throws Exception {
    Account[] accounts = new Account[]{new Account("glass@of.wine", "com.google"), new Account("bad@you.know", "com.google")};
    when(mockAccountManager.getAccountsByType("com.google")).thenReturn(accounts);

    assertThat(subject.fromGoogle(), is(new String[]{"glass@of.wine", "bad@you.know"}));
  }
}