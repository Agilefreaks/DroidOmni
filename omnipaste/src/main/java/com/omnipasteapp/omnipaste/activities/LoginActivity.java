package com.omnipasteapp.omnipaste.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.ViewById;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.omnipasteapp.omnipaste.OmnipasteApplication;
import com.omnipasteapp.omnipaste.R;

import java.util.ArrayList;

import javax.inject.Inject;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_login)
public class LoginActivity extends SherlockActivity {
  @ViewById
  public ListView accountsListView;

  @SystemService
  public AccountManager accountManager;

  @Inject
  public IConfigurationService configurationService;

  private Account[] _accounts;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OmnipasteApplication.inject(this);
  }

  @AfterViews
  public void setAccountsListView() {
    accountsListView.setAdapter(createAccountAdapter(accounts()));
  }

  @ItemClick
  public void accountsListViewItemClicked(int position) {
    saveConfiguration(_accounts[position]);
  }

  //region Private Methods

  private Account[] accounts() {
    if (_accounts == null) {
      _accounts = accountManager.getAccountsByType("com.google");
    }

    return _accounts;
  }

  private ArrayAdapter<String> createAccountAdapter(Account[] accounts) {
    ArrayList<String> names = new ArrayList<String>();
    for(Account account: accounts) {
      names.add(account.name);
    }

    return new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        names);
  }

  private void saveConfiguration(Account account) {
    CommunicationSettings settings = configurationService.getCommunicationSettings();
    settings.setChannel(account.name);
    configurationService.updateCommunicationSettings();
  }

  //endregion
}
