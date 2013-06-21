package com.omnipasteapp.omnipaste;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.ArrayList;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements AdapterView.OnItemClickListener {

  private Account[] _accounts;

  @Inject
  private AccountManager _accountManager;

  @Inject
  private IConfigurationService _configurationService;

  @InjectView(R.id.accounts_listView)
  private ListView _accountListView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    _accountListView.setAdapter(createAccountAdapter(accounts()));
    _accountListView.setOnItemClickListener(this);
  }

  @Override
  public int getMenu() {
    return R.menu.empty_menu;
  }

  public ArrayAdapter<String> createAccountAdapter(Account[] accounts) {
    ArrayList<String> names = new ArrayList<String>();
    for(Account account: accounts) {
      names.add(account.name);
    }

    return new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            names);
  }

  public void login(Account account) {
    saveConfiguration(account);

    _intentService.startService(BackgroundService.class);
    _intentService.startClearActivity(MainActivity.class);
  }

  public Account[] accounts() {
    if (_accounts == null) {
      _accounts = _accountManager.getAccountsByType("com.google");
    }

    return _accounts;
  }

  private void saveConfiguration(Account account) {
    CommunicationSettings settings = _configurationService.getCommunicationSettings();
    settings.setChannel(account.name);
    _configurationService.updateCommunicationSettings();
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
    if (i > -1 && i < accounts().length) {
      login(accounts()[i]);
    }
  }
}