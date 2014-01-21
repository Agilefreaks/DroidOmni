package com.omnipaste.droidomni.adapters;

import android.accounts.Account;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omnipaste.droidomni.views.AccountView;
import com.omnipaste.droidomni.views.AccountView_;

public class AccountAdapter extends BaseAdapter {
  private Account[] accounts;

  public AccountAdapter(Account[] accounts) {
    this.accounts = accounts;
  }

  @Override
  public int getCount() {
    return accounts.length;
  }

  @Override
  public Object getItem(int position) {
    return accounts[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    View accountView;

    if (view != null) {
      accountView = view;
    }
    else {
      Account account = accounts[position];
      accountView = AccountView_.build(viewGroup.getContext());
      ((AccountView) accountView).fillData(account.name);
    }

    return accountView;
  }
}
