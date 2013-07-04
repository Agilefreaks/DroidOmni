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
import com.omnipasteapp.omnipaste.R;

import java.util.ArrayList;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_login)
public class LoginActivity extends SherlockActivity {
  @ViewById
  public ListView accountsListView;

  @SystemService
  public AccountManager accountManager;

  private Account[] _accounts;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @AfterViews
  public void setAccountsListView() {
    accountsListView.setAdapter(createAccountAdapter(accounts()));
  }

  @ItemClick
  public void accountsListViewItemClicked(int position) {

  }

  public Account[] accounts() {
    if (_accounts == null) {
      _accounts = accountManager.getAccountsByType("com.google");
    }

    return _accounts;
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
}
