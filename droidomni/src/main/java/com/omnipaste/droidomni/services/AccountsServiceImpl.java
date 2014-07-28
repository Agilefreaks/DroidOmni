package com.omnipaste.droidomni.services;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.omnipaste.droidomni.DroidOmniApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AccountsServiceImpl implements AccountsService {
  @Inject
  public AccountManager accountManager;

  public AccountsServiceImpl() {
    DroidOmniApplication.inject(this);
  }

  @Override
  public String[] getGoogleEmails() {
    List<String> emails = new ArrayList<>();
    Account[] accountsByType = accountManager.getAccountsByType("com.google");
    for(Account account : accountsByType) {
      emails.add(account.name);
    }

    return emails.toArray(new String[emails.size()]);
  }
}
