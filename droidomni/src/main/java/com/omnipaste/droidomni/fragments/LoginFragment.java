package com.omnipaste.droidomni.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.omnipaste.droidomni.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import fj.F;
import fj.data.Array;

import static fj.data.Array.array;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {
  @ViewById
  public ListView accounts;

  @SystemService
  public AccountManager accountManager;

  @AfterViews
  public void afterView() {
    Array<Account> googleAccounts = array(accountManager.getAccountsByType("com.google"));

    accounts.setAdapter(new ArrayAdapter<>(
        getActivity(),
        android.R.layout.simple_list_item_1,
        googleAccounts.map(new F<Account, String>() {
          @Override
          public String f(Account account) {
            return account.name;
          }
        }).array()));
  }
}
