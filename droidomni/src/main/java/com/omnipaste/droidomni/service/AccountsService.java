package com.omnipaste.droidomni.service;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.omnipaste.droidomni.DroidOmniApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountsService {
  @Inject
  public AccountManager accountManager;

  public AccountsService() {
    DroidOmniApplication.inject(this);
  }

  public String[] getGoogleEmails() {
    List<String> emails = new ArrayList<>();
    Account[] accountsByType = accountManager.getAccountsByType("com.google");
    for(Account account : accountsByType) {
      emails.add(account.name);
    }

    return emails.toArray(new String[emails.size()]);
  }
}
