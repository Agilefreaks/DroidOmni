package com.omnipaste.droidomni.interactions;

import android.accounts.Account;
import android.accounts.AccountManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import fj.F;
import fj.data.Array;

@Singleton
public class GetAccounts {
  private AccountManager accountManager;

  @Inject
  public GetAccounts(AccountManager accountManager) {
    this.accountManager = accountManager;
  }

  public String[] fromGoogle() {
    Array<Account> accounts = Array.array(accountManager.getAccountsByType("com.google"));

    return accounts.map(new F<Account, String>() {
      @Override public String f(Account account) {
        return account.name;
      }
    }).array(String[].class);
  }
}
